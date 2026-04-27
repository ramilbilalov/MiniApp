package org.miniapp.project

actual fun provideTelegramService(): TelegramService = JsTelegramService()

class JsTelegramService : TelegramService {
        override fun initTelegram() {
        // Уже инициализировано в HTML
    }

    override fun getTelegramRawData(): TelegramRawData {
        return try {
            // Проверяем наличие функции getTelegramData
            val hasFunction = js("typeof window.getTelegramData !== 'undefined'") as Boolean
            if (!hasFunction) return getDefaultTelegramData()

            // Вызываем функцию и получаем данные
            val telegramData = js("window.getTelegramData()")
            val isNullData = js("telegramData === null")
            // Проверяем, что функция вернула данные
            if (telegramData == null || isNullData == true) {
                return getDefaultTelegramData()
            }

            TelegramRawData(
                initData = (telegramData.initData as? String) ?: "no_init_data",
                initDataUnsafeRaw = (telegramData.initDataUnsafe as? String) ?: "{}",
                platform = (telegramData.platform as? String) ?: "unknown",
                version = (telegramData.version as? String) ?: "unknown",
                theme = (telegramData.theme as? String) ?: "light"
            )
        } catch (e: Exception) {
            getDefaultTelegramData()
        }
    }

    override fun closeApp() {
        js("""try { 
            if (typeof Telegram !== 'undefined' && Telegram.WebApp) {
                Telegram.WebApp.close(); 
            }
        } catch(e) {
            console.log('Error closing Telegram app:', e);
        }""")
    }

    private fun getDefaultTelegramData(): TelegramRawData {
        return TelegramRawData(
            initData = "not_in_telegram",
            initDataUnsafeRaw = "{}",
            platform = "browser",
            version = "none",
            theme = "light"
        )
    }
}