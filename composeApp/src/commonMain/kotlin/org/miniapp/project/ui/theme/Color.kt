package org.miniapp.project.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─── Палитра ──────────────────────────────────────────────────
// Дизайн-приоритет: высокая разница ПО СВЕТЛОТЕ между ключевыми
// элементами. Это делает интерфейс читаемым при любых формах
// дальтонизма (включая дейтеранопию), а не только для людей
// с полным цветовосприятием.
//
// Стратегия: primary = почти чёрный (CTA), tertiary = коралловый
// (декоративный акцент), errors/success — НЕ только цвет, а ещё
// текст и иконка в коде.

private val Coral = Color(0xFFC96342)              // только декор / accent
private val CoralSoft = Color(0xFFFCE8DD)          // мягкий «персиковый» container

private val Cream50 = Color(0xFFFAF9F5)
private val Cream100 = Color(0xFFF5F4ED)
private val Cream200 = Color(0xFFEDEAE0)
private val Cream300 = Color(0xFFE0DCCD)

private val Stone300 = Color(0xFFD6D3C7)
private val Stone400 = Color(0xFFB4B0A4)
private val Stone500 = Color(0xFF8A8779)
private val Stone600 = Color(0xFF6B6960)
private val Stone700 = Color(0xFF4A4844)
private val Stone800 = Color(0xFF2C2B26)
private val Stone900 = Color(0xFF1F1E1B)

private val ErrorWarm = Color(0xFFB5443D)

val LightClaudeColors: ColorScheme = lightColorScheme(
    // CTA: тёмный — даёт высокий контраст с любой стеклянной карточкой,
    // независимо от цветовосприятия.
    primary = Stone900,
    onPrimary = Cream50,
    primaryContainer = Stone300,
    onPrimaryContainer = Stone900,

    // Декоративный акцент (используется точечно: точка selected, мелкие
    // pill, иконки). НЕ полагайся на него как на единственный сигнал.
    secondary = Coral,
    onSecondary = Color.White,
    secondaryContainer = CoralSoft,
    onSecondaryContainer = Color(0xFF6E2E14),

    tertiary = Color(0xFF6B8E7F),
    onTertiary = Color.White,

    background = Cream50,
    onBackground = Stone900,

    surface = Cream100,
    onSurface = Stone900,
    surfaceVariant = Cream200,
    onSurfaceVariant = Stone600,

    surfaceContainer = Color(0xFFF1EEE3),
    surfaceContainerHigh = Color(0xFFEAE7DC),
    surfaceContainerHighest = Color(0xFFE3E0D5),
    surfaceContainerLow = Cream100,
    surfaceContainerLowest = Color.White,

    outline = Stone400,                            // плотнее, чем было — рамки видны
    outlineVariant = Cream300,

    error = ErrorWarm,
    onError = Color.White,
    errorContainer = Color(0xFFFADBD8),
    onErrorContainer = Color(0xFF5C1810),
)

val DarkClaudeColors: ColorScheme = darkColorScheme(
    // На тёмном CTA — почти белый, для того же luminance-контраста
    primary = Cream50,
    onPrimary = Stone900,
    primaryContainer = Stone700,
    onPrimaryContainer = Cream50,

    secondary = Color(0xFFE08966),                 // мягче коралл на тёмном
    onSecondary = Stone900,
    secondaryContainer = Stone700,
    onSecondaryContainer = CoralSoft,

    tertiary = Color(0xFF8FAB9E),
    onTertiary = Stone900,

    background = Stone900,
    onBackground = Cream50,

    surface = Stone800,
    onSurface = Cream50,
    surfaceVariant = Stone700,
    onSurfaceVariant = Stone400,

    surfaceContainer = Color(0xFF26241F),
    surfaceContainerHigh = Color(0xFF302D27),
    surfaceContainerHighest = Color(0xFF3A372F),
    surfaceContainerLow = Stone900,
    surfaceContainerLowest = Color(0xFF181714),

    outline = Stone500,
    outlineVariant = Stone800,

    error = Color(0xFFE06A60),
    onError = Color.White,
    errorContainer = Color(0xFF5C1810),
    onErrorContainer = Color(0xFFFADBD8),
)
