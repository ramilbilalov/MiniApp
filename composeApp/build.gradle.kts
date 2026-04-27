import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    js(IR) {
        browser {
            outputModuleName = "MiniApp13"
            commonWebpackConfig {
                cssSupport { enabled.set(true) }
                outputFileName = "MiniApp13.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        // ─── Android ──────────────────────────────────────────
        androidMain.dependencies {
            // Compose preview + activity
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Ktor engine для Android
            implementation(libs.ktor.client.okhttp)

            // Корутины-Android (Main dispatcher)
            implementation(libs.kotlinx.coroutines.android)
        }

        // ─── Common (общий KMP) ───────────────────────────────
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Material icons
            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)

            // Lifecycle / ViewModel (KMP)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Navigation Compose (KMP fork)
            implementation(libs.navigation.compose)

            // kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)

            // Coil
            implementation(libs.coilcompose)
            implementation(libs.coil.network.ktor3)

            // Koin (на будущее, если понадобится DI)
            implementation(libs.koin.compose)

            // Ktor client (общая часть)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.logging)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // ─── JS / Web (Telegram Mini App) ─────────────────────
        jsMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.html.core)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Ktor engine для JS (browser fetch)
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "org.miniapp.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.miniapp.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
    buildTypes {
        getByName("release") { isMinifyEnabled = false }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// Если в нескольких source-set'ах окажется одно и то же имя ресурса (например, index.html
// в webMain и jsMain), Gradle по умолчанию падает. Берём последний — это нормальное поведение
// для KMP, где дочерний source-set должен переопределять родительский.
tasks.withType<Copy>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
