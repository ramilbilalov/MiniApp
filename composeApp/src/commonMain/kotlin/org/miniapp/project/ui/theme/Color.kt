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

// ─── Тёмная тема: «оникс» ──────────────────────────────────────
// Глубокий тёплый чёрный с минимальной разницей светлоты между фоном
// и карточками — карточки «плавают» на фоне без резкого outline.
// Текст слегка кремовый (off-white), а не чисто-белый — мягче для глаз
// при длительном чтении (особенно в Legal/FAQ).
private val OnyxBg     = Color(0xFF0F0E0C)   // фон страницы — оникс
private val OnyxSurf   = Color(0xFF18171A)   // карточка чуть выше фона (Δ ~7%)
private val OnyxSurf2  = Color(0xFF1F1E20)   // surfaceVariant
private val OnyxSurf3  = Color(0xFF26252A)   // surfaceContainerHigh
private val OnyxSurf4  = Color(0xFF2D2C32)   // surfaceContainerHighest
private val OnyxLow    = Color(0xFF0B0A09)   // ниже фона (under)
private val OffWhite   = Color(0xFFE7E1D7)   // тёплый off-white (текст)
private val MutedText  = Color(0xFF9A968D)   // приглушённый текст

val DarkClaudeColors: ColorScheme = darkColorScheme(
    // На тёмном CTA — off-white, не чисто-белый. Контраст всё ещё высокий,
    // но не «выжигает» глаза.
    primary = OffWhite,
    onPrimary = OnyxBg,
    primaryContainer = OnyxSurf3,
    onPrimaryContainer = OffWhite,

    secondary = Color(0xFFD9866A),                 // мягче коралл на онксе
    onSecondary = OnyxBg,
    secondaryContainer = OnyxSurf3,
    onSecondaryContainer = CoralSoft,

    tertiary = Color(0xFF8FAB9E),
    onTertiary = OnyxBg,

    background = OnyxBg,
    onBackground = OffWhite,

    surface = OnyxSurf,
    onSurface = OffWhite,
    surfaceVariant = OnyxSurf2,
    onSurfaceVariant = MutedText,

    surfaceContainer = OnyxSurf2,
    surfaceContainerHigh = OnyxSurf3,
    surfaceContainerHighest = OnyxSurf4,
    surfaceContainerLow = OnyxBg,
    surfaceContainerLowest = OnyxLow,

    outline = Color(0xFF6E6A60),
    outlineVariant = Color(0xFF2A2926),

    error = Color(0xFFD9685E),                     // приглушённее, без неона
    onError = OnyxBg,
    errorContainer = Color(0xFF3A1410),
    onErrorContainer = Color(0xFFF2C8C2),
)
