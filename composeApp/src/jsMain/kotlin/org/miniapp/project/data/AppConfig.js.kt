package org.miniapp.project.data

import kotlinx.browser.window

/**
 * На JS читаем `window.BACKEND_URL` из index.html.
 *  • пусто → same-origin (фронт и бэк на одном домене — наш прод).
 *  • явный URL → используем его (полезно если фронт на одном хосте, бэк на другом).
 *  • не задано → fallback на localhost:8080 для разработки.
 */

actual object AppConfig {
    actual val BACKEND_BASE_URL: String
        get() {
            val raw = window.asDynamic().BACKEND_URL as? String
            return when {
                raw == null -> "http://localhost:8080"   // dev: открыли html напрямую
                raw.isBlank() -> window.location.origin  // prod: same-origin
                else -> raw                              // явный URL (фронт и бэк на разных хостах)
            }
        }
}