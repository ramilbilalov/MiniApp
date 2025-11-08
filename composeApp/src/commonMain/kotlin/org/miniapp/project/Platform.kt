package org.miniapp.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform