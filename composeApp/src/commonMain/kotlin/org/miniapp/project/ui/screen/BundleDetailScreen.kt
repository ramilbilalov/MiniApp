package org.miniapp.project.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.miniapp.project.data.Bundle
import org.miniapp.project.data.CatalogRepository
import org.miniapp.project.data.OrdersRepository
import org.miniapp.project.openExternalUrl
import org.miniapp.project.ui.Format
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.isoToFlag
import org.miniapp.project.ui.sheet.PaymentMethod
import org.miniapp.project.ui.sheet.PaymentSheet
import org.miniapp.project.ui.theme.GlassCard
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BundleDetailScreen(
    bundleName: String,
    catalog: CatalogRepository,
    orders: OrdersRepository,
    inTelegram: Boolean,
    onBack: () -> Unit,
    onPurchased: () -> Unit,
) {
    val s = LocalStrings.current
    var bundle by remember { mutableStateOf<Bundle?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var purchasing by remember { mutableStateOf(false) }
    var showPaymentSheet by remember { mutableStateOf(false) }
    var purchaseError by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(bundleName) {
        loading = true
        try { bundle = catalog.bundle(bundleName) }
        catch (e: Throwable) { error = e.message }
        finally { loading = false }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = { Text(bundleName, style = MaterialTheme.typography.titleMedium, maxLines = 1) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = s.back)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
            ),
            windowInsets = androidx.compose.foundation.layout.WindowInsets(0),
        )

        when {
            loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
            error != null -> ErrorState("${s.errorPrefix} $error")
            bundle != null -> Detail(
                bundle = bundle!!,
                purchasing = purchasing,
                onBuyClick = { showPaymentSheet = true },
            )
        }
    }

    if (showPaymentSheet && bundle != null) {
        PaymentSheet(
            purchasing = purchasing,
            purchaseError = purchaseError,
            inTelegram = inTelegram,
            onPick = { method ->
                purchaseError = null
                when (method) {
                    PaymentMethod.CARD -> {
                        // Старт оплаты через RollyPay: backend создаёт платёж,
                        // возвращает pay_url, мы открываем его в браузере и
                        // параллельно начинаем опрос статуса заказа.
                        scope.launch {
                            purchasing = true
                            try {
                                val checkout = orders.startCheckout(
                                    bundleName = bundle!!.name,
                                    gateway = "rollypay",
                                )
                                openExternalUrl(checkout.payUrl)
                                pollOrderStatus(
                                    orders = orders,
                                    orderId = checkout.orderId,
                                    onPaid = {
                                        purchasing = false
                                        showPaymentSheet = false
                                        onPurchased()
                                    },
                                    onFailed = { reason ->
                                        purchasing = false
                                        purchaseError = reason
                                    },
                                )
                            } catch (e: Throwable) {
                                purchasing = false
                                purchaseError = e.message ?: s.paymentFailed
                            }
                        }
                    }
                    PaymentMethod.TELEGRAM_STARS -> {
                        // TODO: подключить Telegram Stars через WebApp.openInvoice.
                        // Пока — заглушка, чтобы было ясно что метод виден но
                        // ещё не работает.
                        purchaseError = s.paymentSoon
                    }
                }
            },
            onDismiss = { if (!purchasing) showPaymentSheet = false },
        )
    }
}

/**
 * Опрос статуса заказа после редиректа на pay_url. Делаем до 3 минут
 * с интервалом 3 секунды. Если за это время не пришло «paid» —
 * пользователь просто остаётся на экране, eSIM появится в "Мои eSIM"
 * когда сервер обработает webhook.
 */
private suspend fun pollOrderStatus(
    orders: OrdersRepository,
    orderId: String,
    onPaid: () -> Unit,
    onFailed: (String) -> Unit,
) {
    val maxAttempts = 60
    repeat(maxAttempts) {
        delay(3000)
        val resp = runCatching { orders.checkStatus(orderId) }.getOrNull() ?: return@repeat
        when (resp.status) {
            "paid"     -> { onPaid(); return }
            "failed",
            "expired"  -> { onFailed(resp.status); return }
            // "pending", "refunded" и прочее — продолжаем ждать
        }
    }
}

@Composable
private fun Detail(bundle: Bundle, purchasing: Boolean, onBuyClick: () -> Unit) {
    val s = LocalStrings.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 120.dp),
        ) {
            // Hero
            GlassCard(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            bundle.countries.firstOrNull()?.iso?.uppercase() ?: "WW",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(
                        bundle.description?.takeIf { it.isNotBlank() } ?: bundle.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Метрики
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                MetricTile(label = s.data, value = Format.data(bundle, s), modifier = Modifier.weight(1f))
                MetricTile(label = s.duration, value = Format.duration(bundle, s), modifier = Modifier.weight(1f))
            }

            if (bundle.countries.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                InfoBlock(
                    label = s.countriesLabel,
                    value = bundle.countries.mapNotNull { it.iso ?: it.name }.joinToString(", "),
                )
            }
            if (bundle.speed.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                InfoBlock(label = s.speedLabel, value = bundle.speed.joinToString(", "))
            }
        }

        // Sticky CTA
        GlassCard(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        s.price,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        Format.price(bundle),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Button(
                    onClick = onBuyClick,
                    enabled = !purchasing,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier.height(52.dp).width(180.dp),
                ) {
                    Text(s.continueToPayment, style = MaterialTheme.typography.labelLarge, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun MetricTile(label: String, value: String, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier, shape = MaterialTheme.shapes.medium) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun InfoBlock(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}
