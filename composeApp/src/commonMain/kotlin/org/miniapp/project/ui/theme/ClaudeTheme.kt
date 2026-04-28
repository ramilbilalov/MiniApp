package org.miniapp.project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import org.miniapp.project.ui.i18n.AppLanguage

enum class AppThemeMode { LIGHT, DARK, SYSTEM }

@Composable
fun ClaudeTheme(
    mode: AppThemeMode = AppThemeMode.SYSTEM,
    language: AppLanguage = AppLanguage.EN,
    content: @Composable () -> Unit,
) {
    val dark = when (mode) {
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val family = appFontFamily(language)
    val typography = remember(family) { ClaudeTypography.applyFontFamily(family) }
    MaterialTheme(
        colorScheme = if (dark) DarkClaudeColors else LightClaudeColors,
        shapes = ClaudeShapes,
        typography = typography,
    ) {
        ProvideGlassColors(dark = dark, content = content)
    }
}

private fun Typography.applyFontFamily(family: FontFamily): Typography = Typography(
    displayLarge = displayLarge.copy(fontFamily = family),
    displayMedium = displayMedium.copy(fontFamily = family),
    displaySmall = displaySmall.copy(fontFamily = family),
    headlineLarge = headlineLarge.copy(fontFamily = family),
    headlineMedium = headlineMedium.copy(fontFamily = family),
    headlineSmall = headlineSmall.copy(fontFamily = family),
    titleLarge = titleLarge.copy(fontFamily = family),
    titleMedium = titleMedium.copy(fontFamily = family),
    titleSmall = titleSmall.copy(fontFamily = family),
    bodyLarge = bodyLarge.copy(fontFamily = family),
    bodyMedium = bodyMedium.copy(fontFamily = family),
    bodySmall = bodySmall.copy(fontFamily = family),
    labelLarge = labelLarge.copy(fontFamily = family),
    labelMedium = labelMedium.copy(fontFamily = family),
    labelSmall = labelSmall.copy(fontFamily = family),
)
