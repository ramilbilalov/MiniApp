package org.miniapp.project.data

import kotlinx.browser.window

private const val KEY = "esimobile.guest_id"

actual object GuestStore {
    actual fun loadGuestId(): String? = runCatching {
        window.localStorage.getItem(KEY)
    }.getOrNull()

    actual fun saveGuestId(id: String) {
        runCatching { window.localStorage.setItem(KEY, id) }
    }
}
