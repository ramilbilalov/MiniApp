package org.miniapp.project.ui

import org.miniapp.project.data.Bundle
import org.miniapp.project.ui.i18n.Strings

object Format {
    fun data(bundle: Bundle, strings: Strings): String {
        if (bundle.unlimited || bundle.dataAmount == -1L) return strings.unlimited
        val mb = bundle.dataAmount ?: return "—"
        if (mb >= 1024) {
            val gb = mb / 1024.0
            return "${trimZero(gb)} GB"
        }
        return "$mb MB"
    }

    fun duration(bundle: Bundle, strings: Strings): String {
        val d = bundle.duration ?: return "—"
        return strings.durationDays.replace("{n}", d.toString())
    }

    fun price(bundle: Bundle): String =
        "${trimZero(bundle.price)} ${bundle.currency}"

    private fun trimZero(value: Double): String {
        val rounded = (value * 100).toLong() / 100.0
        val s = rounded.toString()
        return if (s.endsWith(".0")) s.dropLast(2) else s
    }
}
