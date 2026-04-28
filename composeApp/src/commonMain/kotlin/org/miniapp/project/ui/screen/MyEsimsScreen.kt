package org.miniapp.project.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.data.EsimInfo
import org.miniapp.project.data.EsimsRepository
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.theme.GlassCard

@Composable
fun MyEsimsScreen(
    esims: EsimsRepository,
    onHelpClick: () -> Unit,
) {
    val s = LocalStrings.current
    var loading by remember { mutableStateOf(true) }
    var list by remember { mutableStateOf<List<EsimInfo>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try { list = esims.list() }
        catch (e: Throwable) { error = e.message }
        finally { loading = false }
    }

    LazyColumn(
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    s.myEsimsTitle,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                )
                Surface(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onHelpClick() },
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Text(
                        "?",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        when {
            loading -> item {
                Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }
            error != null -> item { ErrorState("${s.errorPrefix} $error") }
            list.isEmpty() -> item { EmptyState(s.myEsimsEmpty) }
            else -> items(list, key = { it.iccid }) { esim -> EsimCard(esim) }
        }
    }
}

@Composable
private fun EsimCard(esim: EsimInfo) {
    val s = LocalStrings.current
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "SIM",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.size(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        esim.bundleName ?: s.esim,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                    esim.status?.let { StatusBadge(it) }
                }
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(Modifier.height(12.dp))

            KeyValueRow(s.iccid, esim.iccid, mono = true)
            esim.smdpAddress?.let {
                Spacer(Modifier.height(8.dp))
                KeyValueRow(s.smdpLabel, it, mono = true)
            }
            esim.activationCode?.let {
                Spacer(Modifier.height(12.dp))
                Text(
                    s.activationCodeLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        it,
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyValueRow(label: String, value: String, mono: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            value,
            style = if (mono) MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace)
            else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun StatusBadge(status: String) {
    // Кодируем статус НЕ только цветом, но и текстовым маркером и светлотой:
    //   ACTIVE     → "● ACTIVE"     (тёмный текст на ярком фоне — макс. контраст)
    //   PROCESSING → "◐ PROCESSING" (полу-точка — визуальная метафора «в процессе»)
    //   прочие     → "○ <STATUS>"   (пустая точка)
    val isActive = status.uppercase() in setOf("ACTIVE", "ACTIVATED", "INSTALLED")
    val isProcessing = status.uppercase() in setOf("PROCESSING", "PENDING")
    val marker = when {
        isActive     -> "●"
        isProcessing -> "◐"
        else         -> "○"
    }
    val bg = when {
        isActive     -> MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
        isProcessing -> MaterialTheme.colorScheme.surfaceContainerHigh
        else         -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val fg = when {
        isActive     -> MaterialTheme.colorScheme.onPrimary
        else         -> MaterialTheme.colorScheme.onSurface
    }
    Surface(shape = RoundedCornerShape(999.dp), color = bg) {
        Text(
            "$marker  ${status.uppercase()}",
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            fontWeight = FontWeight.Medium,
        )
    }
}
