package org.miniapp.project

/**
 * Платформенное открытие внешнего URL.
 *
 *  • JS вне Telegram: `window.location.href = url` — переадресация в том же
 *    вкладке. Pay-страница RollyPay открывается, потом пользователь возвращается
 *    через `success_redirect_url` (туда мы передали `?order=<orderId>`).
 *  • JS в Telegram Mini App: `Telegram.WebApp.openLink(url)` — открывается во
 *    внешнем браузере, Mini App остаётся открытым.
 *  • Android: `Intent.ACTION_VIEW` (для отладки UI).
 */
expect fun openExternalUrl(url: String)
