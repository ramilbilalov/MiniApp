package org.miniapp.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import miniapp5.composeapp.generated.resources.Inter_Regular
import miniapp5.composeapp.generated.resources.NotoSansArabic_Regular
import miniapp5.composeapp.generated.resources.NotoSansSC_Regular
import miniapp5.composeapp.generated.resources.NotoSans_Regular
import miniapp5.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import org.miniapp.project.ui.i18n.AppLanguage

/**
 * FontFamily приложения, выбираемая по текущему языку.
 *
 * Стратегия:
 *  • EN/RU и т.п. (Latin / Cyrillic) → **Inter** — современный grotesque
 *    с очень читаемой формой, фирменный для tech-стартапов. Покрывает
 *    Latin / Latin-Ext / Cyrillic / Greek / Vietnamese.
 *  • ZH → Noto Sans SC (Inter не покрывает CJK).
 *  • AR → Noto Sans Arabic (Inter не покрывает арабский).
 *
 * Внутри FontFamily перечислены fallback-шрифты — если Skiko не найдёт
 * глиф в Inter, он попробует следующий шрифт. Это важно для смешанных
 * языковых строк (например, английский текст с русским словом).
 *
 * На JS-таргете Skia/Skiko не имеет встроенных шрифтов, поэтому грузим
 * variable-фонты из `composeResources/font/` (см. `fetch-fonts.sh`).
 *
 * `Font(...)` — это @Composable функция, поэтому вызывается напрямую
 * в @Composable контексте, без обёртки `remember { ... }`.
 */
@Composable
fun appFontFamily(language: AppLanguage): FontFamily = when (language) {
    AppLanguage.ZH -> FontFamily(
        Font(Res.font.NotoSansSC_Regular),
        Font(Res.font.Inter_Regular),         // fallback для латиницы/цифр
    )
    AppLanguage.AR -> FontFamily(
        Font(Res.font.NotoSansArabic_Regular),
        Font(Res.font.Inter_Regular),         // fallback для латиницы/цифр
    )
    else -> FontFamily(
        Font(Res.font.Inter_Regular),
        Font(Res.font.NotoSans_Regular),      // fallback на случай редких символов
    )
}
