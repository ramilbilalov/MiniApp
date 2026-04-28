package org.miniapp.project.ui

import org.miniapp.project.data.Bundle
import org.miniapp.project.ui.i18n.AppLanguage

/** Сводка по стране для списка на главном экране. */
data class CountrySummary(
    val iso: String,
    val englishName: String,
    val flagEmoji: String,
    val plansCount: Int,
    val fromPrice: Double,
    val currency: String,
    val region: String?,
) {
    /** Имя на текущем языке (для отображения). */
    fun displayName(language: AppLanguage): String =
        CountryNames.displayName(iso, language, englishName)

    /** Все имена для поиска (en + ru + zh + ar + iso). */
    fun searchableNames(): List<String> =
        CountryNames.searchableNames(iso, englishName)
}

/** Из плоского списка бандлов делаем сводки по странам. */
fun List<Bundle>.toCountrySummaries(): List<CountrySummary> {
    val perIso = mutableMapOf<String, MutableList<Pair<Bundle, org.miniapp.project.data.Country>>>()
    for (bundle in this) {
        for (country in bundle.countries) {
            val iso = country.iso?.takeIf { it.length == 2 } ?: continue
            perIso.getOrPut(iso.uppercase()) { mutableListOf() }.add(bundle to country)
        }
    }
    return perIso.entries.map { (iso, items) ->
        val cheapest = items.minByOrNull { it.first.price }!!
        CountrySummary(
            iso = iso,
            englishName = cheapest.second.name ?: iso,
            flagEmoji = isoToFlag(iso),
            plansCount = items.size,
            fromPrice = cheapest.first.price,
            currency = cheapest.first.currency,
            region = cheapest.second.region,
        )
    }.sortedBy { it.englishName }
}

/** Все бандлы, в `countries` которых есть данный ISO. */
fun List<Bundle>.forCountry(iso: String): List<Bundle> =
    filter { b -> b.countries.any { it.iso.equals(iso, true) } }
        .sortedBy { it.price }

/** ISO-код страны → emoji-флаг. */
fun isoToFlag(iso: String): String {
    if (iso.length != 2) return "🌐"
    val base = 0x1F1E6 - 'A'.code
    val a = iso[0].uppercaseChar()
    val b = iso[1].uppercaseChar()
    return buildString {
        appendCodePoint(base + a.code)
        appendCodePoint(base + b.code)
    }
}

private fun StringBuilder.appendCodePoint(cp: Int): StringBuilder {
    if (cp < 0x10000) append(cp.toChar())
    else {
        val high = ((cp - 0x10000) shr 10) + 0xD800
        val low = ((cp - 0x10000) and 0x3FF) + 0xDC00
        append(high.toChar())
        append(low.toChar())
    }
    return this
}

// ─── Фильтры ─────────────────────────────────────────────────
enum class PriceRange(val min: Double, val max: Double) {
    ANY(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY),
    UNDER_5(0.0, 5.0),
    BETWEEN_5_15(5.0, 15.0),
    OVER_15(15.0, Double.POSITIVE_INFINITY),
}

enum class DataRange(val minMb: Long, val maxMb: Long, val unlimitedOnly: Boolean = false) {
    ANY(0, Long.MAX_VALUE),
    UNDER_1GB(0, 1024),
    BETWEEN_1_5GB(1024, 5 * 1024L),
    BETWEEN_5_15GB(5 * 1024L, 15 * 1024L),
    OVER_15GB(15 * 1024L, Long.MAX_VALUE),
    UNLIMITED(0, Long.MAX_VALUE, unlimitedOnly = true);
}

enum class DurationRange(val minDays: Int, val maxDays: Int) {
    ANY(0, Int.MAX_VALUE),
    UNDER_7(0, 7),
    BETWEEN_8_30(8, 30),
    OVER_30(31, Int.MAX_VALUE),
}

enum class SortBy { CHEAPEST, MORE_DATA, LONGER }

fun Bundle.matches(price: PriceRange, data: DataRange, duration: DurationRange): Boolean {
    if (this.price < price.min - 1e-9 || this.price > price.max + 1e-9) return false

    val mb = dataAmount ?: 0L
    val isUnlimited = unlimited || mb == -1L
    when (data) {
        DataRange.UNLIMITED -> if (!isUnlimited) return false
        DataRange.ANY -> Unit
        else -> {
            if (isUnlimited) return data == DataRange.OVER_15GB
            if (mb < data.minMb || mb > data.maxMb) return false
        }
    }

    val d = this.duration ?: 0
    if (d < duration.minDays || d > duration.maxDays) return false
    return true
}

fun List<Bundle>.sortedBy(sort: SortBy): List<Bundle> = when (sort) {
    SortBy.CHEAPEST   -> sortedBy { it.price }
    SortBy.MORE_DATA  -> sortedByDescending { if (it.unlimited || it.dataAmount == -1L) Long.MAX_VALUE else (it.dataAmount ?: 0) }
    SortBy.LONGER     -> sortedByDescending { it.duration ?: 0 }
}
