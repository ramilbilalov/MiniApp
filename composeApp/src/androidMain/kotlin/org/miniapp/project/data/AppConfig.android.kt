package org.miniapp.project.data

actual object AppConfig {
    // На Android-таргете прописываем напрямую (или через BuildConfig если понадобится).
    actual val BACKEND_BASE_URL: String = "http://10.0.2.2:8080"
}
