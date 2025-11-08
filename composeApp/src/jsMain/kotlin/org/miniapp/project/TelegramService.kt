package org.miniapp.project

import kotlin.js.Json
import kotlinx.browser.window

external interface TelegramWebApp {
    val initData: String
    val initDataUnsafe: InitDataUnsafe
    val version: String
    val platform: String
    val colorScheme: String
    val themeParams: Json
    val isExpanded: Boolean
    val viewportHeight: Number
    val viewportStableHeight: Number
    val headerColor: String
    val backgroundColor: String
    fun ready()
    fun expand()
    fun close()
    fun showAlert(message: String)
    fun showConfirm(message: String, callback: (confirmed: Boolean) -> Unit)
}

external interface InitDataUnsafe {
    val user: User?
    val receiver: User?
    val chat: Json?
    val startParam: String?
    val authDate: Number?
    val hash: String?
}

external interface User {
    val id: Number
    val first_name: String?
    val last_name: String?
    val username: String?
    val language_code: String?
}

// Глобальный объект Telegram, который предоставляет Telegram
external val Telegram: dynamic

class JsTelegramService : TelegramService {
    private val webApp: TelegramWebApp
        get() = getTelegramWebApp()

    private fun getTelegramWebApp(): TelegramWebApp {
        return if (isInTelegram()) {
            // Безопасное получение через js()
            js("Telegram.WebApp").unsafeCast<TelegramWebApp>()
        } else {
            createMockWebApp()
        }
    }

    private fun createMockWebApp(): TelegramWebApp {
        // Мок для разработки вне Telegram
        return object : TelegramWebApp {
            override val initData: String = ""
            override val initDataUnsafe: InitDataUnsafe = object : InitDataUnsafe {
                override val user: User? = null
                override val receiver: User? = null
                override val chat: Json? = null
                override val startParam: String? = null
                override val authDate: Number? = null
                override val hash: String? = null
            }
            override val version: String = "mock"
            override val platform: String = "web"
            override val colorScheme: String = "light"
            override val themeParams: Json = js("{}")
            override val isExpanded: Boolean = true
            override val viewportHeight: Number = 600
            override val viewportStableHeight: Number = 600
            override val headerColor: String = "#000000"
            override val backgroundColor: String = "#ffffff"

            override fun ready() = console.log("Mock Telegram ready")
            override fun expand() = console.log("Mock expand")
            override fun close() = console.log("Mock close")
            override fun showAlert(message: String) = window.alert(message)
            override fun showConfirm(message: String, callback: (Boolean) -> Unit) {
                val confirmed = window.confirm(message)
                callback(confirmed)
            }
        }
    }

    override fun initTelegram() {
        if (isInTelegram()) {
            webApp.ready()
            // Устанавливаем цвет фона если в Telegram
            webApp.expand() // Раскрываем на весь экран
        }
    }

    override fun getTelegramData(): TelegramData {
        val userData = webApp.initDataUnsafe.user
        val user = userData?.let { user ->
            TelegramUser(
                id = user.id.toString(),
                firstName = user.first_name,
                lastName = user.last_name,
                username = user.username,
                languageCode = user.language_code,
            )
        }

        return TelegramData(
            initData = webApp.initData,
            user = user,
            startParam = webApp.initDataUnsafe.startParam,
            platform = webApp.platform,
            theme = webApp.colorScheme,
        )
    }

    override fun closeApp() {
        if (isInTelegram()) {
            webApp.close()
        } else {
            // В браузере просто переходим на главную
            window.location.href = "/"
        }
    }

    private fun isInTelegram(): Boolean {
        return js("typeof Telegram !== 'undefined' && Telegram.WebApp") as Boolean
    }
}

actual fun provideTelegramService(): TelegramService = JsTelegramService()