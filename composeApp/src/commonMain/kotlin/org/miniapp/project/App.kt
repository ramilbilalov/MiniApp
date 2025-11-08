package org.miniapp.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    var telegramData by remember { mutableStateOf<TelegramData?>(null) }
    var debugLogs = remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var showAlert by remember { mutableStateOf(false) }

    // Функция для добавления логов
    fun addDebugLog(message: String) {
        println("DEBUG: $message")
        debugLogs.value += message
        // Ограничиваем количество логов
    }

    // Список элементов для демонстрации
    val sampleItems:List<String> = listOf(
        "Apple", "Banana", "Orange", "Grapes", "Strawberry",
        "Pineapple", "Mango", "Watermelon", "Kiwi", "Peach"
    )

    LaunchedEffect(Unit) {
        addDebugLog("🔄 LaunchedEffect started")
        try {
            addDebugLog("Initializing Telegram...")
            telegramService.initTelegram()
            addDebugLog("✅ Telegram initialized")

            val data = telegramService.getTelegramData()
            addDebugLog("📱 Data received: ${true}")
            telegramData = data
        } catch (e: Exception) {
            addDebugLog("❌ Error: ${e.message}")
        }
    }

    // AlertDialog для показа выбранного элемента
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Выбранный элемент") },
            text = {
                Text("Вы выбрали: $selectedItem\nID: ${selectedItem?.hashCode()}")
            },
            confirmButton = {
                Button(
                    onClick = { showAlert = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize().wrapContentHeight(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Заголовок
                    Text(
                        text = "🍎 Fruit List",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Информация о Telegram
                    telegramData?.let { data ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "👤 ${data.user?.firstName ?: "User"}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "📱 ${data.platform} • ${data.theme}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Список элементов
                    Text(
                        "Выберите фрукт:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn {
                        items(sampleItems) { item ->
                            ListItem(
                                item = item,
                                onClick = {
                                    selectedItem = item
                                    showAlert = true
                                    addDebugLog("🎯 Selected: $item (ID: ${item.hashCode()})")
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Кнопка закрытия
                    Button(
                        onClick = {
                            telegramService.closeApp()
                            addDebugLog("🔴 App closed")
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Close Mini App")
                    }
                }

                // Панель логов
                DebugPanel(
                    logs = debugLogs.value,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
fun ListItem(item: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DebugPanel(logs: List<String>, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .width(300.dp)
            .height(if (expanded) 200.dp else 68.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Заголовок панели логов
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { expanded = !expanded }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "📝 Debug Logs",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp
                )

            }
            if (expanded) {
                LazyColumn {
                    items(logs) { item ->
                        Text(
                            text = item
                        )
                    }
                }
            }

        }
    }
}
