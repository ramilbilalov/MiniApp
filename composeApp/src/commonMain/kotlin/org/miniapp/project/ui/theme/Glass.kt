package org.miniapp.project.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Палитра liquid-glass:
 *  • базовый цвет страницы + сдержанные радиальные тинты в углах,
 *  • полупрозрачные карточки с ВЫСОКИМ контрастом светлоты,
 *  • заметная белёсая рамка.
 *
 * Контрасты выбраны так, чтобы UI оставался читаемым при дейтеранопии
 * (когда зелёно-красный канал слабый — основное различие должно идти
 * через светлоту, а не через цветовой тон).
 */
data class GlassColors(
    val pageBase: Color,
    val tintTopRight: Color,
    val tintBottomLeft: Color,
    val cardFillTop: Color,
    val cardFillBottom: Color,
    val cardBorder: Color,
)

internal val LightGlass = GlassColors(
    pageBase       = Color(0xFFEFEDE3),                            // чуть темнее кремового, чтобы стекло стояло выше
    tintTopRight   = Color(0xFFFCD9C2).copy(alpha = 0.50f),        // мягче, не «жжёт» глаза
    tintBottomLeft = Color(0xFFE8DDD0).copy(alpha = 0.40f),        // тёпло-серый вместо коралла — лучше для дейтеранопии
    cardFillTop    = Color.White.copy(alpha = 0.86f),              // карточка ЯВНО светлее фона
    cardFillBottom = Color.White.copy(alpha = 0.66f),
    cardBorder     = Color.White.copy(alpha = 0.92f),              // выраженная блик-рамка
)

internal val DarkGlass = GlassColors(
    // Оникс: фон ровный и глубокий, карточки лишь чуть-чуть выше фона
    // (Δ светлоты маленькая → нет резкого "обреза" вокруг карточки).
    // Тинты в углах едва заметные, чтобы не было ощущения «дискотеки» на тёмном.
    pageBase       = Color(0xFF0F0E0C),
    tintTopRight   = Color(0xFF3D2C36).copy(alpha = 0.22f),
    tintBottomLeft = Color(0xFF24303F).copy(alpha = 0.20f),
    cardFillTop    = Color.White.copy(alpha = 0.06f),
    cardFillBottom = Color.White.copy(alpha = 0.025f),
    cardBorder     = Color.White.copy(alpha = 0.10f),
)

val LocalGlassColors = staticCompositionLocalOf { LightGlass }

@Composable
fun ProvideGlassColors(dark: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalGlassColors provides if (dark) DarkGlass else LightGlass,
        content = content,
    )
}

/** Фон страницы: база + два сдержанных радиальных блика в противоположных углах. */
@Composable
fun GlassBackground(content: @Composable () -> Unit) {
    val g = LocalGlassColors.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(g.pageBase)
            .background(
                Brush.radialGradient(
                    colors = listOf(g.tintTopRight, Color.Transparent),
                    center = Offset(Float.POSITIVE_INFINITY, 0f),
                    radius = 1400f,
                ),
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(g.tintBottomLeft, Color.Transparent),
                    center = Offset(0f, Float.POSITIVE_INFINITY),
                    radius = 1400f,
                ),
            ),
    ) { content() }
}

/**
 * Liquid-glass карточка с высокой читаемостью:
 *  • белая полупрозрачная заливка (vertical gradient) — заметно светлее фона,
 *  • рамка 1dp, виден как блик-кант.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val g = LocalGlassColors.current
    val base = modifier
        .clip(shape)
        .background(
            brush = Brush.verticalGradient(listOf(g.cardFillTop, g.cardFillBottom)),
            shape = shape,
        )
        .border(width = 1.dp, color = g.cardBorder, shape = shape)
    val withClick = if (onClick != null) base.clickable { onClick() } else base
    Box(modifier = withClick) { content() }
}
