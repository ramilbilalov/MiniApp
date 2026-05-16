package org.miniapp.project.data

/**
 * Платформенное хранилище гостевого UUID.
 *
 *  • На JS — `window.localStorage`.
 *  • На Android — `SharedPreferences` (файл `esimobile_prefs`).
 *
 * Хранит **один** ключ — `esimobile.guest_id`. Если в будущем понадобятся
 * другие persistent-настройки (например тема/язык по умолчанию), удобнее
 * расширить этот же объект, чем плодить отдельные expect-API.
 */
expect object GuestStore {
    /** Прочитать сохранённый guestId или null если его ещё нет. */
    fun loadGuestId(): String?

    /** Сохранить guestId, чтобы при следующем заходе получить тот же JWT. */
    fun saveGuestId(id: String)
}
