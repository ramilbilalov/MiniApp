package org.miniapp.project.ui

import org.miniapp.project.TelegramService
import org.miniapp.project.data.AuthRepository
import org.miniapp.project.data.BackendClient
import org.miniapp.project.data.CatalogRepository
import org.miniapp.project.data.EsimsRepository
import org.miniapp.project.data.OrdersRepository

/**
 * Простой DI-контейнер без Koin — для MVP достаточно.
 * Создаётся один раз в композиции и прокидывается через CompositionLocal.
 */
class AppContainer(telegramService: TelegramService) {
    val backend = BackendClient()
    val auth = AuthRepository(backend, telegramService)
    val catalog = CatalogRepository(backend)
    val orders = OrdersRepository(backend)
    val esims = EsimsRepository(backend)
}
