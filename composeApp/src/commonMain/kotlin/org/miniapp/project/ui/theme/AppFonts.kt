package org.miniapp.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import miniapp5.composeapp.generated.resources.NotoSansArabic_Regular
import miniapp5.composeapp.generated.resources.NotoSansSC_Regular
import miniapp5.composeapp.generated.resources.NotoSans_Regular
import miniapp5.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import org.miniapp.project.ui.i18n.AppLanguage

/**
 * FontFamily приложения, выбираемая по текущему языку.
 *
 * На JS-таргете Skia/Skiko не имеет встроенных шрифтов с кириллицей,
 * CJK и арабским — без явного ресурса всё неанглийское рисуется
 * квадратами «.notdef». Поэтому грузим Noto Sans (variable) из
 * `composeResources/font/`. Variable-фонт сам обслуживает все веса,
 * поэтому один файл на язык.
 *
 * `Font(...)` — это @Composable функция, поэтому вызывается напрямую
 * в @Composable контексте, без обёртки `remember { ... }`.
 */
@Composable
fun appFontFamily(language: AppLanguage): FontFamily = when (language) {
    AppLanguage.ZH -> FontFamily(Font(Res.font.NotoSansSC_Regular))
    AppLanguage.AR -> FontFamily(Font(Res.font.NotoSansArabic_Regular))
    else           -> FontFamily(Font(Res.font.NotoSans_Regular))
}
