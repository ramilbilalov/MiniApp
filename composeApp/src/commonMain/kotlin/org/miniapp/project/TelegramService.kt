package org.miniapp.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class TelegramUser(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val username: String?,
    val languageCode: String?
)

@Immutable
data class TelegramData(
    val initData: String,
    val user: TelegramUser?,
    val startParam: String?,
    val platform: String,
    val theme: String
)

interface TelegramService {
    fun initTelegram()
    fun getTelegramData(): TelegramData
    fun closeApp()
}

// Provider для доступа к сервису
expect fun provideTelegramService(): TelegramService

// Глобальный экземпляр для удобства
val telegramService: TelegramService get() = provideTelegramService()