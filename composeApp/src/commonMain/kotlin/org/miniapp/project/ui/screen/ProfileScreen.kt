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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.data.AuthResponse
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.theme.AppThemeMode
import org.miniapp.project.ui.theme.GlassCard

@Composable
fun ProfileScreen(
    session: AuthResponse?,
    authError: String?,
    themeMode: AppThemeMode,
    onThemeChange: (AppThemeMode) -> Unit,
    onHelpClick: () -> Unit,
    onLegalClick: () -> Unit,
) {
    val s = LocalStrings.current
    LazyColumn(
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Text(
                s.profileTitle,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
        }
        if (authError != null) item { AuthErrorCard(s.authFailedTitle, authError) }
        item {
            if (session != null) UserCard(session, s.anonymous, s.telegramId)
            else NoSessionCard(s.notInTelegram)
        }
        item {
            ThemeCard(
                title = s.themeLabel,
                lightLabel = s.themeLight,
                darkLabel = s.themeDark,
                systemLabel = s.themeSystem,
                current = themeMode,
                onChange = onThemeChange,
            )
        }
        item { HelpCard(label = s.openHelp, onClick = onHelpClick) }
        item { HelpCard(label = s.openLegal, onClick = onLegalClick) }
        item { SupportCard(s.supportTitle, s.supportText) }
    }
}

@Composable
private fun HelpCard(label: String, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                label,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                "›",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun UserCard(session: AuthResponse, anonymousLabel: String, telegramIdLabel: String) {
    val name = listOfNotNull(session.user.first_name, session.user.last_name)
        .joinToString(" ").ifBlank { anonymousLabel }
    val initials = name.split(" ").mapNotNull { it.firstOrNull()?.uppercase() }.take(2).joinToString("")

    GlassCard(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        initials.ifBlank { "?" },
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(Modifier.size(14.dp))
                Column {
                    Text(name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    session.user.username?.let {
                        Text("@$it", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(telegramIdLabel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(session.user.id.toString(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Composable
private fun NoSessionCard(text: String) {
    GlassCard(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Text(text, modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun AuthErrorCard(title: String, message: String) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.errorContainer) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
        }
    }
}

@Composable
private fun ThemeCard(
    title: String,
    lightLabel: String,
    darkLabel: String,
    systemLabel: String,
    current: AppThemeMode,
    onChange: (AppThemeMode) -> Unit,
) {
    GlassCard(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Column(Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                ThemeChip(systemLabel, AppThemeMode.SYSTEM == current, Modifier.weight(1f)) { onChange(AppThemeMode.SYSTEM) }
                ThemeChip(lightLabel, AppThemeMode.LIGHT == current, Modifier.weight(1f)) { onChange(AppThemeMode.LIGHT) }
                ThemeChip(darkLabel, AppThemeMode.DARK == current, Modifier.weight(1f)) { onChange(AppThemeMode.DARK) }
            }
        }
    }
}

@Composable
private fun ThemeChip(label: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Box(Modifier.fillMaxWidth().height(44.dp), contentAlignment = Alignment.Center) {
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun SupportCard(title: String, message: String) {
    GlassCard(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Column(Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
