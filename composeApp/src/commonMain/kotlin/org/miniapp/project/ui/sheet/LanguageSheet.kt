package org.miniapp.project.ui.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.ui.i18n.AppLanguage
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.theme.appFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSheet(
    current: AppLanguage,
    onPick: (AppLanguage) -> Unit,
    onDismiss: () -> Unit,
) {
    val s = LocalStrings.current
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = state,
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 24.dp)) {
            Text(
                s.chooseLanguage,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(16.dp))
            AppLanguage.entries.forEach { lang ->
                LanguageRow(lang, selected = lang == current, onClick = {
                    onPick(lang)
                    onDismiss()
                })
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun LanguageRow(lang: AppLanguage, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh
    val fg = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    // Имя языка рендерим ЕГО ШРИФТОМ — иначе 中文/العربية, написанные шрифтом
    // текущего UI (например NotoSans), показываются квадратами.
    val nativeFont = appFontFamily(lang)

    Surface(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = bg,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    lang.code.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.size(12.dp))
            Text(
                lang.displayName,
                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = nativeFont),
                color = fg,
            )
            Spacer(Modifier.weight(1f))
            if (selected) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
            }
        }
    }
}
