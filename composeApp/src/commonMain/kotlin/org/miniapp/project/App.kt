package org.miniapp.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import org.miniapp.project.data.AuthResponse
import org.miniapp.project.ui.AppContainer
import org.miniapp.project.ui.i18n.AppLanguage
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.i18n.LocaleProvider
import org.miniapp.project.legal.LegalDocs
import org.miniapp.project.ui.screen.BundleDetailScreen
import org.miniapp.project.ui.screen.CatalogScreen
import org.miniapp.project.ui.screen.CountryScreen
import org.miniapp.project.ui.screen.DocumentScreen
import org.miniapp.project.ui.screen.FaqScreen
import org.miniapp.project.ui.screen.HelpScreen
import org.miniapp.project.ui.screen.LegalScreen
import org.miniapp.project.ui.screen.MyEsimsScreen
import org.miniapp.project.ui.screen.ProfileScreen
import org.miniapp.project.ui.sheet.LanguageSheet
import org.miniapp.project.ui.theme.AppThemeMode
import org.miniapp.project.ui.theme.ClaudeTheme
import org.miniapp.project.ui.theme.GlassBackground
import androidx.compose.ui.graphics.Color

private object Routes {
    const val CATALOG = "catalog"
    const val MY_ESIMS = "my_esims"
    const val PROFILE = "profile"
    const val HELP = "help"
    const val LEGAL = "legal"
    const val FAQ = "faq"

    const val COUNTRY = "country/{iso}"
    fun country(iso: String) = "country/$iso"

    const val DOCUMENT = "document/{type}"
    fun document(type: LegalDocs.Doc) = "document/${type.name}"

    const val BUNDLE_DETAIL = "bundle/{name}"
    fun bundleDetail(name: String) = "bundle/$name"
}

@Composable
fun App() {
    val telegramService = remember { provideTelegramService() }
    val container = remember { AppContainer(telegramService) }

    var loading by remember { mutableStateOf(true) }
    var authError by remember { mutableStateOf<String?>(null) }
    var session by remember { mutableStateOf<AuthResponse?>(null) }
    var language by remember { mutableStateOf(AppLanguage.EN) }
    var themeMode by remember { mutableStateOf(AppThemeMode.SYSTEM) }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var inTelegram by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        runCatching { telegramService.initTelegram() }
        val raw = runCatching { telegramService.getTelegramRawData() }.getOrNull()
        language = AppLanguage.fromCode(detectLangFromInitData(raw?.initDataUnsafeRaw))
        // Считаем что мы внутри Telegram, если platform даёт реальный клиент
        // (не "browser" / "unknown" из fallback-объекта).
        inTelegram = raw?.platform?.let { it != "browser" && it != "unknown" && it != "android_dev" } == true
        try {
            // В Telegram — авторизуемся через initData (HMAC).
            // Вне Telegram — гостевая сессия с UUID из localStorage.
            session = if (inTelegram) {
                container.auth.signInWithTelegram()
            } else {
                container.auth.signInAsGuest()
            }
            session?.user?.languageCode?.let { language = AppLanguage.fromCode(it) }
        } catch (e: Throwable) {
            authError = e.message ?: "Auth failed"
        } finally {
            loading = false
        }
    }

    ClaudeTheme(mode = themeMode, language = language) {
        LocaleProvider(language) {
            GlassBackground {
                if (loading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(strokeWidth = 2.dp)
                    }
                } else {
                    MainScaffold(
                        container = container,
                        session = session,
                        authError = authError,
                        currentLanguage = language,
                        onLanguageClick = { showLanguageSheet = true },
                        themeMode = themeMode,
                        onThemeChange = { themeMode = it },
                        inTelegram = inTelegram,
                    )
                }

                if (showLanguageSheet) {
                    LanguageSheet(
                        current = language,
                        onPick = { language = it },
                        onDismiss = { showLanguageSheet = false },
                    )
                }
            }
        }
    }
}

private fun detectLangFromInitData(raw: String?): String? {
    if (raw.isNullOrBlank()) return null
    val key = "\"language_code\":\""
    val i = raw.indexOf(key).takeIf { it >= 0 } ?: return null
    val start = i + key.length
    val end = raw.indexOf('"', start).takeIf { it > start } ?: return null
    return raw.substring(start, end)
}

@Composable
private fun MainScaffold(
    container: AppContainer,
    session: AuthResponse?,
    authError: String?,
    currentLanguage: AppLanguage,
    onLanguageClick: () -> Unit,
    themeMode: AppThemeMode,
    onThemeChange: (AppThemeMode) -> Unit,
    inTelegram: Boolean,
) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val s = LocalStrings.current

    Scaffold(
        containerColor = Color.Transparent,
        // Status-bar inset применяем ОДИН раз на уровне Scaffold.
        // У внутренних TopAppBar выключаем собственный windowInsets (см. экраны),
        // тогда они не «дрожат» при переключении между маршрутами.
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
            ) {
                NavigationBarItem(
                    selected = currentRoute?.startsWith("catalog") == true ||
                        currentRoute?.startsWith("country") == true ||
                        currentRoute?.startsWith("bundle") == true,
                    onClick = {
                        navController.navigate(Routes.CATALOG) {
                            popUpTo(Routes.CATALOG) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    label = { Text(s.tabCatalog) },
                    colors = navColors(),
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.MY_ESIMS,
                    onClick = { navController.navigate(Routes.MY_ESIMS) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    label = { Text(s.tabMyEsims) },
                    colors = navColors(),
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.PROFILE,
                    onClick = { navController.navigate(Routes.PROFILE) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text(s.tabProfile) },
                    colors = navColors(),
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.CATALOG,
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            composable(Routes.CATALOG) {
                CatalogScreen(
                    catalog = container.catalog,
                    user = session?.user,
                    currentLanguage = currentLanguage,
                    onLanguageClick = onLanguageClick,
                    onCountryClick = { iso -> navController.navigate(Routes.country(iso)) },
                )
            }
            composable(Routes.COUNTRY) { entry ->
                val iso = entry.arguments?.read { getString("iso") }.orEmpty()
                CountryScreen(
                    iso = iso,
                    catalog = container.catalog,
                    onBack = { navController.popBackStack() },
                    onPlanClick = { name -> navController.navigate(Routes.bundleDetail(name)) },
                )
            }
            composable(Routes.BUNDLE_DETAIL) { entry ->
                val name = entry.arguments?.read { getString("name") }.orEmpty()
                BundleDetailScreen(
                    bundleName = name,
                    catalog = container.catalog,
                    orders = container.orders,
                    inTelegram = inTelegram,
                    onBack = { navController.popBackStack() },
                    onPurchased = {
                        navController.navigate(Routes.MY_ESIMS) {
                            popUpTo(Routes.CATALOG)
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(Routes.MY_ESIMS) {
                MyEsimsScreen(
                    esims = container.esims,
                    onHelpClick = { navController.navigate(Routes.HELP) },
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    session = session,
                    authError = authError,
                    themeMode = themeMode,
                    onThemeChange = onThemeChange,
                    onHelpClick = { navController.navigate(Routes.HELP) },
                    onLegalClick = { navController.navigate(Routes.LEGAL) },
                )
            }
            composable(Routes.HELP) {
                HelpScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.LEGAL) {
                LegalScreen(
                    onBack = { navController.popBackStack() },
                    onOpenDocument = { type -> navController.navigate(Routes.document(type)) },
                    onOpenFaq = { navController.navigate(Routes.FAQ) },
                )
            }
            composable(Routes.FAQ) {
                FaqScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.DOCUMENT) { entry ->
                val typeName = entry.arguments?.read { getString("type") }.orEmpty()
                val doc = runCatching { LegalDocs.Doc.valueOf(typeName) }
                    .getOrDefault(LegalDocs.Doc.PRIVACY)
                DocumentScreen(doc = doc, onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
private fun navColors() = NavigationBarItemDefaults.colors(
    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
    selectedTextColor = MaterialTheme.colorScheme.onSurface,
    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
)
