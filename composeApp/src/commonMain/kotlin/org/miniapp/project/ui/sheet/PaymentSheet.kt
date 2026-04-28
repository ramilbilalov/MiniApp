package org.miniapp.project.ui.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import org.miniapp.project.ui.i18n.LocalStrings

enum class PaymentMethod { TELEGRAM_STARS, CARD, CRYPTO }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSheet(
    purchasing: Boolean,
    purchaseError: String?,
    inTelegram: Boolean,
    onPick: (PaymentMethod) -> Unit,
    onDismiss: () -> Unit,
) {
    val s = LocalStrings.current
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { if (!purchasing) onDismiss() },
        sheetState = state,
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 24.dp)) {
            Text(
                s.choosePayment,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(16.dp))

            // Telegram Stars показываем ТОЛЬКО когда приложение реально запущено
            // в Telegram WebApp — иначе оплатить Stars невозможно.
            if (inTelegram) {
                PayRow(
                    method = PaymentMethod.TELEGRAM_STARS,
                    title = s.payTelegramStars,
                    subtitle = s.paymentSoon,
                    enabled = !purchasing,
                    onClick = { onPick(PaymentMethod.TELEGRAM_STARS) },
                )
                Spacer(Modifier.height(8.dp))
            }
            PayRow(
                method = PaymentMethod.CARD,
                title = s.payCard,
                subtitle = s.paymentSoon,
                enabled = !purchasing,
                onClick = { onPick(PaymentMethod.CARD) },
            )
            Spacer(Modifier.height(8.dp))
            PayRow(
                method = PaymentMethod.CRYPTO,
                title = s.payCrypto,
                subtitle = s.paymentSoon,
                enabled = !purchasing,
                onClick = { onPick(PaymentMethod.CRYPTO) },
            )

            if (purchasing) {
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.size(12.dp))
                    Text(s.loading, style = MaterialTheme.typography.bodyMedium)
                }
            }
            purchaseError?.let {
                Spacer(Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                ) {
                    Text(
                        "${s.errorPrefix} $it",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            }
        }
    }
}

@Composable
private fun PayRow(
    method: PaymentMethod,
    title: String,
    subtitle: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                when (method) {
                    PaymentMethod.TELEGRAM_STARS -> Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(22.dp),
                    )
                    PaymentMethod.CARD -> Text(
                        "CARD",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                    )
                    PaymentMethod.CRYPTO -> Text(
                        "BTC",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
