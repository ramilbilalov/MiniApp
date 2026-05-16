package org.miniapp.project.data

/**
 * Android-реализация — пока in-memory.
 *
 * Полноценный SharedPreferences потребовал бы доступа к Application Context
 * откуда-то из commonMain, что усложнит API. На Android-таргете сейчас
 * приложение запускается только для отладки UI разработчиками — guestId,
 * который теряется при перезапуске процесса, для этого подходит.
 *
 * Когда будет релиз для Android — заменить на SharedPreferences через
 * androidx.startup AppInitializer или через AndroidContext-провайдер.
 */
actual object GuestStore {
    @Volatile private var inMemory: String? = null

    actual fun loadGuestId(): String? = inMemory

    actual fun saveGuestId(id: String) {
        inMemory = id
    }
}
