package org.miniapp.project.data

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BackendException(val statusCode: Int, message: String) : RuntimeException(message)

/**
 * Клиент к нашему Ktor-серверу (esimstore).
 * Mini App никогда не ходит в eSIM Go напрямую — только через бэк.
 */
class BackendClient(
    val baseUrl: String = AppConfig.BACKEND_BASE_URL,
) {
    private var token: String? = null

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = true
        coerceInputValues = true
    }

    private val http = createPlatformHttpClient {
        expectSuccess = false
        install(ContentNegotiation) { json(json) }
        install(Logging) { level = LogLevel.INFO }
        defaultRequest {
            url(baseUrl.trimEnd('/') + "/")
            header(HttpHeaders.Accept, "application/json")
            token?.let { header(HttpHeaders.Authorization, "Bearer $it") }
        }
    }

    fun setToken(value: String?) { token = value }
    fun token(): String? = token

    // ─── Auth ─────────────────────────────────────────────────
    suspend fun authTelegram(initData: String): AuthResponse {
        val resp = http.post("auth/telegram") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(initData))
        }
        ensureOk(resp)
        return resp.body<AuthResponse>().also { setToken(it.token) }
    }

    // ─── Catalogue ────────────────────────────────────────────
    suspend fun getCatalogue(
        countries: List<String> = emptyList(),
        region: String? = null,
        fast: Boolean = false,
    ): CatalogueResponse {
        val resp = http.get("api/catalogue") {
            if (countries.isNotEmpty()) parameter("countries", countries.joinToString(","))
            region?.let { parameter("region", it) }
            if (fast) parameter("fast", "true")
        }
        ensureOk(resp)
        return resp.body()
    }

    suspend fun getBundle(name: String): Bundle {
        val resp = http.get("api/catalogue/bundles/$name")
        ensureOk(resp)
        return resp.body()
    }

    // ─── Orders ───────────────────────────────────────────────
    suspend fun createOrder(req: CreateOrderRequest): CreateOrderResponse {
        val resp = http.post("api/orders") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }
        ensureOk(resp)
        return resp.body()
    }

    // ─── eSIMs ────────────────────────────────────────────────
    suspend fun getMyEsims(): List<EsimInfo> {
        val resp = http.get("api/esims")
        ensureOk(resp)
        return resp.body<MyEsimsResponse>().esims
    }

    private suspend fun ensureOk(resp: HttpResponse) {
        if (resp.status.isSuccess()) return
        val body = runCatching { resp.bodyAsText() }.getOrDefault("")
        throw BackendException(resp.status.value, "HTTP ${resp.status.value}: $body")
    }

    fun close() = http.close()
}
