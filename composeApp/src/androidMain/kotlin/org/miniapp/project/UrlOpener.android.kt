package org.miniapp.project

/**
 * Android-реализация — заглушка для отладочной сборки. На реальном Android-релизе
 * понадобится Context — тогда переключимся на startActivity(Intent.ACTION_VIEW).
 */
actual fun openExternalUrl(url: String) {
    println("openExternalUrl (android stub): $url")
}
