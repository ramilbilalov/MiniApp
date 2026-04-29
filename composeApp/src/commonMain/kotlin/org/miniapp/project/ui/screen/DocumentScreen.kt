package org.miniapp.project.ui.screen

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.miniapp.project.legal.LegalDocs
import org.miniapp.project.ui.i18n.LocalStrings

/**
 * Универсальный рендерер длинных документов (Privacy, Terms, Complaints, Bug Bounty).
 *
 * Простой markdown-подобный парсер: `# title`, `## subtitle`, `- bullet`,
 * пустые строки → разделители, всё остальное → параграфы.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScreen(doc: LegalDocs.Doc, onBack: () -> Unit) {
    val s = LocalStrings.current
    val text = remember(doc) { LegalDocs.textFor(doc) }
    val blocks = remember(text) { parseDocument(text) }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    doc.titleEn,
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
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            blocks.forEachIndexed { index, block ->
                item(key = index) { RenderBlock(block) }
            }
        }
    }
}

@Composable
private fun RenderBlock(block: DocBlock) {
    when (block) {
        is DocBlock.H1 -> {
            Spacer(Modifier.height(8.dp))
            Text(
                block.text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            )
        }
        is DocBlock.H2 -> {
            Spacer(Modifier.height(12.dp))
            Text(
                block.text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        is DocBlock.Bullet -> {
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp),
                )
                Text(
                    block.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        is DocBlock.Paragraph -> {
            Text(
                block.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        DocBlock.Spacer -> Spacer(Modifier.height(4.dp))
    }
}

// ─── Парсер ───────────────────────────────────────────────────────

private sealed interface DocBlock {
    data class H1(val text: String) : DocBlock
    data class H2(val text: String) : DocBlock
    data class Paragraph(val text: String) : DocBlock
    data class Bullet(val text: String) : DocBlock
    data object Spacer : DocBlock
}

private fun parseDocument(text: String): List<DocBlock> {
    val blocks = mutableListOf<DocBlock>()
    val paragraphLines = mutableListOf<String>()

    fun flushParagraph() {
        if (paragraphLines.isEmpty()) return
        blocks.add(DocBlock.Paragraph(paragraphLines.joinToString(" ")))
        paragraphLines.clear()
    }

    text.lineSequence().forEach { rawLine ->
        val line = rawLine.trimEnd()
        when {
            line.isBlank() -> {
                flushParagraph()
                blocks.add(DocBlock.Spacer)
            }
            line.startsWith("## ") -> {
                flushParagraph()
                blocks.add(DocBlock.H2(line.removePrefix("## ").trim()))
            }
            line.startsWith("# ") -> {
                flushParagraph()
                blocks.add(DocBlock.H1(line.removePrefix("# ").trim()))
            }
            line.startsWith("- ") -> {
                flushParagraph()
                blocks.add(DocBlock.Bullet(line.removePrefix("- ").trim()))
            }
            else -> {
                paragraphLines.add(line.trimStart())
            }
        }
    }
    flushParagraph()
    return blocks
}
