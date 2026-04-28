package org.miniapp.project.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

/**
 * Создаёт платформенный HttpClient (на Android — OkHttp, на JS — fetch).
 * Конфиг общий: его передаём в [block], engine выбирается через expect.
 */
expect fun createPlatformHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient
