package org.miniapp.project

/**
 * На Android Mini App запускается обычно как нативное Compose-приложение
 * (либо в Telegram через WebView — там используется JS-таргет).
 * Здесь — fallback-данные, чтобы commonMain мог дёргать сервис без условий.
 */
actual fun provideTelegramService(): TelegramService = AndroidTelegramService()

class AndroidTelegramService : TelegramService {
    override fun initTelegram() = Unit

    override fun getTelegramRawData(): TelegramRawData = TelegramRawData(
        initData = "android_dev",
        initDataUnsafeRaw = "{}",
        platform = "android",
        version = "1.0",
        theme = "light",
    )

    override fun closeApp() = Unit
}
