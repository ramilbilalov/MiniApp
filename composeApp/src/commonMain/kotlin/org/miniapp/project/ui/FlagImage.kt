package org.miniapp.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import miniapp5.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap

/**
 * Кружок с PNG-флагом страны.
 *
 * Файлы лежат в `composeResources/files/flags/<iso>.png`, скачаны из
 * Twemoji (см. `composeApp/fetch-flags.sh`).
 *
 * Кэш — на уровне процесса. ImageBitmap'ы держатся в памяти, поэтому
 * для одного и того же ISO `Res.readBytes` дёргается только один раз.
 *
 * Если файла нет (eSIM Go отдал нестандартный псевдо-ISO вроде "X1") —
 * показываем буквы как fallback. То есть никогда не падаем.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun FlagImage(iso: String, size: Dp, modifier: Modifier = Modifier) {
    val key = remember(iso) { iso.lowercase() }
    var bitmap by remember(key) {
        mutableStateOf<ImageBitmap?>(FlagCache.get(key))
    }
    var failed by remember(key) { mutableStateOf(false) }

    // Если в кэше нет — асинхронно подгружаем байты из ресурсов и декодируем.
    LaunchedEffect(key) {
        if (bitmap != null || failed) return@LaunchedEffect
        try {
            val bytes = Res.readBytes("files/flags/$key.png")
            val bmp = bytes.decodeToImageBitmap()
            FlagCache.put(key, bmp)
            bitmap = bmp
        } catch (_: Throwable) {
            failed = true
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        val bmp = bitmap
        if (bmp != null) {
            Image(
                bitmap = bmp,
                contentDescription = iso,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(size),
            )
        } else {
            // Fallback: ISO-код (пока не загрузился или флага нет в наборе).
            Text(
                iso.uppercase(),
                style = if (size > 50.dp) MaterialTheme.typography.titleLarge
                        else MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

/** Простейший process-level кэш ImageBitmap по ISO. */
private object FlagCache {
    private val map = mutableMapOf<String, ImageBitmap>()
    fun get(key: String): ImageBitmap? = map[key]
    fun put(key: String, value: ImageBitmap) { map[key] = value }
}
