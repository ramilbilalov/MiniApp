package org.miniapp.project.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.legal.Faq
import org.miniapp.project.ui.i18n.LocalAppLanguage
import org.miniapp.project.ui.i18n.LocalStrings
import org.miniapp.project.ui.theme.GlassCard

/**
 * Экран FAQ. Группы → вопросы → expandable ответы.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(onBack: () -> Unit) {
    val s = LocalStrings.current
    val language = LocalAppLanguage.current
    val groups = remember(language) { Faq.groupsFor(language) }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    s.legalFaq,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = s.back)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
            windowInsets = WindowInsets(0),
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            groups.forEachIndexed { groupIndex, group ->
                item(key = "header_$groupIndex") {
                    Spacer(Modifier.height(if (groupIndex == 0) 4.dp else 12.dp))
                    Text(
                        group.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                    )
                }
                group.items.forEachIndexed { itemIndex, faq ->
                    item(key = "item_${groupIndex}_$itemIndex") {
                        FaqCard(faq)
                    }
                }
            }
        }
    }
}

@Composable
private fun FaqCard(item: Faq.Item) {
    var expanded by rememberSaveable(item.question) { mutableStateOf(false) }
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = MaterialTheme.shapes.large,
        onClick = { expanded = !expanded },
    ) {
        Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
            ) {
                Text(
                    item.question,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    if (expanded) "−" else "+",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (expanded) {
                Spacer(Modifier.height(10.dp))
                AnswerText(item.answer)
            }
        }
    }
}

/**
 * Лёгкий рендер ответа: bullets, параграфы, **bold** не парсим, но
 * сохраняем переносы между блоками.
 */
@Composable
private fun AnswerText(text: String) {
    val lines = remember(text) { text.split("\n") }
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        val paragraph = StringBuilder()
        val flushParagraph: () -> Unit = {}
        // Простой проход: соседние не-bullet строки склеиваем в один параграф.
        val blocks = mutableListOf<Pair<Boolean, String>>() // isBullet → text
        val buf = StringBuilder()
        lines.forEach { raw ->
            val line = raw.trimEnd()
            when {
                line.isBlank() -> {
                    if (buf.isNotEmpty()) { blocks.add(false to buf.toString().trim()); buf.clear() }
                }
                line.trimStart().startsWith("- ") -> {
                    if (buf.isNotEmpty()) { blocks.add(false to buf.toString().trim()); buf.clear() }
                    blocks.add(true to line.trimStart().removePrefix("- ").trim())
                }
                else -> {
                    if (buf.isNotEmpty()) buf.append(' ')
                    buf.append(line.trim())
                }
            }
        }
        if (buf.isNotEmpty()) blocks.add(false to buf.toString().trim())

        blocks.forEach { (isBullet, content) ->
            if (isBullet) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        "•",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else {
                Text(
                    content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
