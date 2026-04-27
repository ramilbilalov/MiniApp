package org.miniapp.project

import androidx.compose.runtime.Immutable

@Immutable
data class TelegramRawData(
    val initData: String,
    val initDataUnsafeRaw: String, // Просто строка с JSON
    val platform: String,
    val version: String,
    val theme: String
)

interface TelegramService {
    fun initTelegram()
    fun getTelegramRawData(): TelegramRawData
    fun closeApp()
}

// Provider (должен быть в commonMain)
expect fun provideTelegramService(): TelegramService

// Глобальный экземпляр для удобства
val telegramService: TelegramService get() = provideTelegramService()

