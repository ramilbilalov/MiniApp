package org.miniapp.project

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    var telegramData by remember { mutableStateOf<TelegramData?>(null) }

    LaunchedEffect(Unit) {
        // Инициализируем Telegram и получаем данные
        telegramService.initTelegram()
        telegramData = telegramService.getTelegramData()
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Telegram Mini App",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                telegramData?.user?.let { user ->
                    UserInfoCard(user)
                } ?: run {
                    Text("Loading Telegram data...")
                }

                telegramData?.let { data ->
                    AppInfoCard(data)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        telegramService.closeApp()
                    }
                ) {
                    Text("Close Mini App")
                }
            }
        }
    }
}

// Остальные композейблы остаются без изменений
@Composable
fun UserInfoCard(user: TelegramUser) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "User Information",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("ID: ${user.id ?: "N/A"}")
            Text("Name: ${user.firstName ?: ""} ${user.lastName ?: ""}")
            Text("Username: @${user.username ?: "N/A"}")
            Text("Language: ${user.languageCode ?: "N/A"}")
        }
    }
}

@Composable
fun AppInfoCard(data: TelegramData) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "App Information",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Platform: ${data.platform}")
            Text("Theme: ${data.theme}")
            Text("Start Param: ${data.startParam ?: "N/A"}")
        }
    }
}