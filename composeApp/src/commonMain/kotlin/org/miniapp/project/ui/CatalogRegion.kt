package org.miniapp.project.ui

import org.miniapp.project.data.Bundle
import org.miniapp.project.ui.i18n.Strings

enum class CatalogRegion(val titleKey: (Strings) -> String) {
    ALL({ it.regionAll }),
    EUROPE({ it.regionEurope }),
    ASIA({ it.regionAsia }),
    AMERICAS({ it.regionAmericas }),
    AFRICA({ it.regionAfrica }),
    MIDDLE_EAST({ it.regionMiddleEast }),
    OCEANIA({ it.regionOceania }),
    GLOBAL({ it.regionGlobal });
}

/** Применяет фильтр региона к списку бандлов. */
fun List<Bundle>.byRegion(region: CatalogRegion): List<Bundle> = when (region) {
    CatalogRegion.ALL -> this
    else -> {
        val keywords = region.keywords()
        filter { b ->
            b.groups.any { g -> keywords.any { k -> g.contains(k, true) } } ||
                b.countries.any { c -> keywords.any { k -> c.region?.contains(k, true) == true } }
        }
    }
}

private fun CatalogRegion.keywords(): List<String> = when (this) {
    CatalogRegion.EUROPE       -> listOf("europe", "eu")
    CatalogRegion.ASIA         -> listOf("asia")
    CatalogRegion.AMERICAS     -> listOf("america", "north america", "south america", "americas")
    CatalogRegion.AFRICA       -> listOf("africa")
    CatalogRegion.MIDDLE_EAST  -> listOf("middle east", "middleeast", "gulf")
    CatalogRegion.OCEANIA      -> listOf("oceania", "australia", "pacific")
    CatalogRegion.GLOBAL       -> listOf("global", "world", "regional")
    CatalogRegion.ALL          -> emptyList()
}
