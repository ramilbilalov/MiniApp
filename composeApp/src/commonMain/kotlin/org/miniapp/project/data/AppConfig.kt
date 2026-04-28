package org.miniapp.project.data

/**
 * Базовый URL Ktor-сервера. На JS-таргете подменяется через window.BACKEND_URL
 * (см. index.html, который генерится при деплое или подставляется вручную).
 *
 * На Android (если когда-то будет нативный таргет) — пишем напрямую сюда.
 */
expect object AppConfig {
    val BACKEND_BASE_URL: String
}
