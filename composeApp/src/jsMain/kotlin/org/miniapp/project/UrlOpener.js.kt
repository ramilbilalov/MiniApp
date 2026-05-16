package org.miniapp.project

import kotlinx.browser.window

actual fun openExternalUrl(url: String) {
    val tg = window.asDynamic().Telegram
    // В Telegram Mini App: используем openLink — открывает внешний браузер,
    // не уходя из Mini App. Это нужно потому что pay-страница RollyPay
    // имеет свои редиректы внутри СБП-приложений и не любит, когда её
    // запихивают в iframe Telegram WebView.
    if (tg != null && tg.WebApp != null) {
        try {
            tg.WebApp.openLink(url)
            return
        } catch (_: Throwable) {
            // fallback ниже
        }
    }
    // Обычный браузер (гостевой режим вне Telegram): просто редирект.
    window.location.href = url
}
