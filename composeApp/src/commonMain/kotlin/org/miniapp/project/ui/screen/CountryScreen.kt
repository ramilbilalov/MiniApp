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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.animateContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.data.Bundle
import org.miniapp.project.data.CatalogRepository
import org.miniapp.project.ui.CountryNames
import org.miniapp.project.ui.DataRange
import org.miniapp.project.ui.DurationRange
import org.miniapp.project.ui.Format
import org.miniapp.project.ui.PriceRange
import org.miniapp.project.ui.SortBy
import org.miniapp.project.ui.forCountry
import org.miniapp.project.ui.i18n.AppLanguage
import org.miniapp.project.ui.i18n.LocalAppLanguage
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.isoToFlag
import org.miniapp.project.ui.matches
import org.miniapp.project.ui.sortedBy
import org.miniapp.project.ui.theme.GlassCard
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(
    iso: String,
    catalog: CatalogRepository,
    onBack: () -> Unit,
    onPlanClick: (String) -> Unit,
) {
    val s = LocalStrings.current
    val lang = LocalAppLanguage.current

    var bundles by remember { mutableStateOf<List<Bundle>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var price by remember { mutableStateOf(PriceRange.ANY) }
    var data by remember { mutableStateOf(DataRange.ANY) }
    var duration by remember { mutableStateOf(DurationRange.ANY) }
    var sort by remember { mutableStateOf(SortBy.CHEAPEST) }

    LaunchedEffect(iso) {
        loading = bundles.isEmpty()
        try {
            // Берём из единого кэша каталога — без повторного похода в бэк
            bundles = catalog.forCountry(iso).sortedBy { it.price }
        } catch (e: Throwable) { error = e.message }
        finally { loading = false }
    }

    val visible by remember(bundles, price, data, duration, sort) {
        derivedStateOf {
            bundles.filter { it.matches(price, data, duration) }.sortedBy(sort)
        }
    }

    val englishName = bundles.firstOrNull()
        ?.countries?.firstOrNull { it.iso.equals(iso, true) }?.name ?: iso
    val countryName = CountryNames.displayName(iso, lang, englishName)

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    countryName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                )
            },
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

        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            item { Hero(iso = iso, name = countryName) }

            item {
                FiltersBlock(
                    price = price, onPrice = { price = it },
                    data = data, onData = { data = it },
                    duration = duration, onDuration = { duration = it },
                    sort = sort, onSort = { sort = it },
                    onReset = {
                        price = PriceRange.ANY; data = DataRange.ANY
                        duration = DurationRange.ANY; sort = SortBy.CHEAPEST
                    },
                )
            }

            item {
                Text(
                    "${visible.size} · ${s.plansForCountry.replace("{country}", countryName)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            when {
                loading -> item {
                    Box(Modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                }
                error != null -> item { ErrorState("${s.errorPrefix} $error") }
                visible.isEmpty() -> item { EmptyState(s.catalogEmpty) }
                else -> items(visible, key = { it.name }) { bundle ->
                    PlanCard(bundle, onClick = { onPlanClick(bundle.name) })
                }
            }
        }
    }
}

@Composable
private fun Hero(iso: String, name: String) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    iso.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.size(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    iso.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun FiltersBlock(
    price: PriceRange, onPrice: (PriceRange) -> Unit,
    data: DataRange, onData: (DataRange) -> Unit,
    duration: DurationRange, onDuration: (DurationRange) -> Unit,
    sort: SortBy, onSort: (SortBy) -> Unit,
    onReset: () -> Unit,
) {
    val s = LocalStrings.current
    // По умолчанию фильтры свёрнуты — карточка занимает мало места,
    // пользователь видит сразу список тарифов. Открываем по тапу.
    var expanded by remember { mutableStateOf(false) }

    // Считаем сколько фильтров активно — для бейджа в свёрнутом состоянии.
    val activeCount = listOf(
        price != PriceRange.ANY,
        data != DataRange.ANY,
        duration != DurationRange.ANY,
        sort != SortBy.CHEAPEST,
    ).count { it }

    GlassCard(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        shape = MaterialTheme.shapes.large,
        onClick = { if (!expanded) expanded = true },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Шапка: название + (опц. бейдж активных) + кнопка свернуть/развернуть
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    s.filtersTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
                if (activeCount > 0) {
                    Spacer(Modifier.size(8.dp))
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = MaterialTheme.colorScheme.primary,
                    ) {
                        Text(
                            activeCount.toString(),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (expanded) {
                Spacer(Modifier.size(8.dp))

                FilterGroup(s.dataLabel) {
                    ChipRow(
                        items = listOf(
                            DataRange.ANY to s.priceAny,
                            DataRange.UNDER_1GB to s.dataUnder1,
                            DataRange.BETWEEN_1_5GB to s.data1to5,
                            DataRange.BETWEEN_5_15GB to s.data5to15,
                            DataRange.OVER_15GB to s.data15plus,
                            DataRange.UNLIMITED to s.dataUnlimited,
                        ),
                        selected = data,
                        onSelect = onData,
                    )
                }
                Spacer(Modifier.size(10.dp))

                FilterGroup(s.durationLabel) {
                    ChipRow(
                        items = listOf(
                            DurationRange.ANY to s.priceAny,
                            DurationRange.UNDER_7 to s.durationUnder7,
                            DurationRange.BETWEEN_8_30 to s.duration8to30,
                            DurationRange.OVER_30 to s.duration30plus,
                        ),
                        selected = duration,
                        onSelect = onDuration,
                    )
                }
                Spacer(Modifier.size(10.dp))

                FilterGroup(s.priceFilter) {
                    ChipRow(
                        items = listOf(
                            PriceRange.ANY to s.priceAny,
                            PriceRange.UNDER_5 to s.priceUnder5,
                            PriceRange.BETWEEN_5_15 to s.price5to15,
                            PriceRange.OVER_15 to s.price15plus,
                        ),
                        selected = price,
                        onSelect = onPrice,
                    )
                }
                Spacer(Modifier.size(10.dp))

                FilterGroup(s.sortLabel) {
                    ChipRow(
                        items = listOf(
                            SortBy.CHEAPEST to s.sortCheapest,
                            SortBy.MORE_DATA to s.sortMoreData,
                            SortBy.LONGER to s.sortLonger,
                        ),
                        selected = sort,
                        onSelect = onSort,
                    )
                }

                // Reset — внизу справа карточки, видна только в развёрнутом виде.
                Spacer(Modifier.size(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onReset) { Text(s.filterReset) }
                }
            }
        }
    }
}

@Composable
private fun FilterGroup(label: String, content: @Composable () -> Unit) {
    Column {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.size(6.dp))
        content()
    }
}

@Composable
private fun <T> ChipRow(items: List<Pair<T, String>>, selected: T, onSelect: (T) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) { (value, label) ->
            val isOn = value == selected
            if (isOn) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clip(RoundedCornerShape(999.dp)).clickable { onSelect(value) },
                ) {
                    Text(
                        label,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium,
                    )
                }
            } else {
                GlassCard(
                    shape = RoundedCornerShape(999.dp),
                    onClick = { onSelect(value) },
                ) {
                    Text(
                        label,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

@Composable
private fun PlanCard(bundle: Bundle, onClick: () -> Unit) {
    val s = LocalStrings.current
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        Format.data(bundle, s),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        Format.duration(bundle, s),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        Format.price(bundle),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            bundle.description?.takeIf { it.isNotBlank() }?.let {
                Spacer(Modifier.size(8.dp))
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                )
            }
        }
    }
}
