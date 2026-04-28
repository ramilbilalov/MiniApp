package org.miniapp.project.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.miniapp.project.TelegramService

class AuthRepository(
    private val backend: BackendClient,
    private val telegramService: TelegramService,
) {
    suspend fun signInWithTelegram(): AuthResponse {
        val raw = telegramService.getTelegramRawData()
        return backend.authTelegram(raw.initData)
    }

    fun isAuthenticated() = backend.token() != null
    fun signOut() { backend.setToken(null) }
}

/**
 * Каталог в памяти приложения. Концепция:
 *   • `quick()`           — главный экран: первая страница каталога (~100+ стран). 1–2 сек cold.
 *   • `forCountry(iso)`   — экран страны: запрос с фильтром `?countries=XX` к бэку,
 *                            возвращает все тарифы только этой страны (1–2 сек).
 *   • `bundle(name)`      — деталь: ищем в кэше, иначе ходим в бэк.
 *
 * Полный каталог из 5000+ бандлов мы НИКОГДА не грузим — это и не нужно для пользователя.
 * Бэк дополнительно держит дисковый кэш и SWR.
 */
class CatalogRepository(private val backend: BackendClient) {
    private val mutex = Mutex()

    private var quickCached: CatalogueResponse? = null
    private var quickAt: kotlin.time.TimeMark? = null

    private val countryCache = mutableMapOf<String, Pair<List<Bundle>, kotlin.time.TimeMark>>()
    private val countryMutex = Mutex()

    private val ttl: kotlin.time.Duration = kotlin.time.Duration.parse("10m")

    private fun fresh(at: kotlin.time.TimeMark?): Boolean =
        at != null && at.elapsedNow() < ttl

    /** Что есть прямо сейчас в quick, без сети. null если ничего. */
    fun cachedNow(): CatalogueResponse? =
        quickCached?.takeIf { fresh(quickAt) }

    /**
     * Главный экран — запрашиваем общий каталог без фильтров.
     * Сервер сам решает: отдать full из кэша или quick + пройти full в фоне.
     */
    suspend fun quick(): CatalogueResponse = mutex.withLock {
        if (fresh(quickAt)) return@withLock quickCached!!
        val fresh = backend.getCatalogue()
        quickCached = fresh
        quickAt = kotlin.time.TimeSource.Monotonic.markNow()
        fresh
    }

    /**
     * Бандлы для конкретной страны. Сначала пытаемся отдать из локального кэша
     * (всё, что уже есть в общем quick), иначе ходим на бэк с фильтром.
     */
    suspend fun forCountry(iso: String): List<Bundle> = countryMutex.withLock {
        val key = iso.uppercase()
        countryCache[key]?.takeIf { fresh(it.second) }?.let { return@withLock it.first }

        // Если общий каталог уже подгружен и в нём есть бандлы для этой страны —
        // мгновенно отдаём отфильтрованное.
        val fromGeneral = quickCached?.bundles
            ?.filter { b -> b.countries.any { it.iso.equals(key, ignoreCase = true) } }
            ?.sortedBy { it.price }
            .orEmpty()
        if (fromGeneral.isNotEmpty()) {
            countryCache[key] = fromGeneral to kotlin.time.TimeSource.Monotonic.markNow()
            return@withLock fromGeneral
        }

        // Иначе — точечный запрос к бэку только за этой страной.
        val resp = backend.getCatalogue(countries = listOf(key))
        val list = resp.bundles
            .filter { b -> b.countries.any { it.iso.equals(key, ignoreCase = true) } }
            .sortedBy { it.price }
        countryCache[key] = list to kotlin.time.TimeSource.Monotonic.markNow()
        list
    }

    /** Деталь бандла. */
    suspend fun bundle(name: String): Bundle {
        quickCached?.bundles?.firstOrNull { it.name == name }?.let { return it }
        countryCache.values.firstNotNullOfOrNull { (list, _) -> list.firstOrNull { it.name == name } }
            ?.let { return it }
        return backend.getBundle(name)
    }

    fun invalidate() {
        quickCached = null; quickAt = null
        countryCache.clear()
    }
}

class OrdersRepository(private val backend: BackendClient) {
    suspend fun createOrder(bundleName: String, quantity: Int = 1) =
        backend.createOrder(CreateOrderRequest(bundleName = bundleName, quantity = quantity))
}

class EsimsRepository(private val backend: BackendClient) {
    suspend fun list() = backend.getMyEsims()
}

