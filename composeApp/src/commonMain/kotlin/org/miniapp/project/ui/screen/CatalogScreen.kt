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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import org.miniapp.project.data.TelegramUser
import org.miniapp.project.ui.CatalogRegion
import org.miniapp.project.ui.CountrySummary
import org.miniapp.project.ui.FlagImage
import org.miniapp.project.ui.byRegion
import org.miniapp.project.ui.i18n.AppLanguage
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.theme.GlassCard
import org.miniapp.project.ui.toCountrySummaries
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    catalog: CatalogRepository,
    user: TelegramUser?,
    currentLanguage: AppLanguage,
    onLanguageClick: () -> Unit,
    onCountryClick: (String) -> Unit,
) {
    val s = LocalStrings.current
    var bundles by remember { mutableStateOf<List<Bundle>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var query by remember { mutableStateOf("") }
    var region by remember { mutableStateOf(CatalogRegion.ALL) }

    LaunchedEffect(Unit) {
        // Главный экран — только первая страница каталога. Остальные тарифы
        // подгрузятся динамически когда пользователь тапнет конкретную страну.
        catalog.cachedNow()?.let { bundles = it.bundles; loading = false }
        if (bundles.isEmpty()) {
            try { bundles = catalog.quick().bundles }
            catch (e: Throwable) { error = e.message }
            finally { loading = false }
        }
    }

    val countries by remember(bundles, region, query, currentLanguage) {
        derivedStateOf {
            val list = bundles.byRegion(region).toCountrySummaries()
            val q = query.trim()
            if (q.isEmpty()) list
            else list.filter { c ->
                // Совпадение по любому из языковых вариантов имени + ISO + локализованное имя
                (c.searchableNames() + c.displayName(currentLanguage))
                    .any { it.contains(q, ignoreCase = true) }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "eSIMobile",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            actions = {
                LanguageFlagButton(currentLanguage, onClick = onLanguageClick)
                Spacer(Modifier.size(4.dp))
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
            item {
                Greeting(name = user?.first_name)
                Spacer(Modifier.height(16.dp))
                SearchBar(query = query, onChange = { query = it }, hint = s.countriesSearch)
                Spacer(Modifier.height(12.dp))
                RegionsRow(selected = region, onSelect = { region = it })
                Spacer(Modifier.height(4.dp))
            }
            when {
                loading -> items(6) { CountryCardSkeleton() }
                error != null -> item { ErrorState("${s.errorPrefix} $error") }
                countries.isEmpty() -> item { EmptyState(s.catalogEmpty) }
                else -> items(countries, key = { it.iso }) { country ->
                    CountryCard(country, language = currentLanguage, onClick = { onCountryClick(country.iso) })
                }
            }
        }
    }
}

@Composable
private fun Greeting(name: String?) {
    Column {
        if (!name.isNullOrBlank()) {
            Text(
                "Hi, $name",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(2.dp))
        }
        Text(
            LocalStrings.current.catalogTitle,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(query: String, onChange: (String) -> Unit, hint: String) {
    OutlinedTextField(
        value = query,
        onValueChange = onChange,
        placeholder = { Text(hint) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.55f),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.45f),
            focusedContainerColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.55f),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun RegionsRow(selected: CatalogRegion, onSelect: (CatalogRegion) -> Unit) {
    val s = LocalStrings.current
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(CatalogRegion.entries) { region ->
            val isOn = region == selected
            if (isOn) {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clip(RoundedCornerShape(999.dp)).clickable { onSelect(region) },
                ) {
                    Text(
                        region.titleKey(s),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            } else {
                GlassCard(
                    shape = RoundedCornerShape(999.dp),
                    onClick = { onSelect(region) },
                ) {
                    Text(
                        region.titleKey(s),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
private fun LanguageFlagButton(language: AppLanguage, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        onClick = onClick,
    ) {
        Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
            Text(
                language.code.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun CountryCard(country: CountrySummary, language: AppLanguage, onClick: () -> Unit) {
    val s = LocalStrings.current
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // PNG-флаг от Twemoji (см. ui/FlagImage.kt + composeApp/fetch-flags.sh).
            // Кэшируется в памяти, так что одна и та же страна декодируется
            // только один раз, даже если карточка рендерится много раз.
            FlagImage(iso = country.iso, size = 44.dp)
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    country.displayName(language),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    s.plansCount.replace("{n}", country.plansCount.toString()),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                Text(
                    s.fromPrice.replace("{price}", "$${formatPrice(country.fromPrice)}"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }
        }
    }
}

private fun formatPrice(p: Double): String {
    val cents = (p * 100).toLong()
    return if (cents % 100 == 0L) (cents / 100).toString() else "${cents / 100}.${(cents % 100).toString().padStart(2, '0')}"
}

@Composable
private fun CountryCardSkeleton() {
    GlassCard(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.6f)),
            )
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .width(140.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.6f)),
                )
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .height(10.dp)
                        .width(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.4f)),
                )
            }
            Box(
                modifier = Modifier
                    .height(22.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.6f)),
            )
        }
    }
}

@Composable
internal fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp).padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
internal fun ErrorState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(16.dp),
    ) {
        Text(
            message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}
