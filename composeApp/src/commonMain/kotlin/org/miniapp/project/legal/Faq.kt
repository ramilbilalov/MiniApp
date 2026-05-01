package org.miniapp.project.legal

import org.miniapp.project.ui.i18n.AppLanguage

/**
 * Часто задаваемые вопросы.
 *
 * Тексты разнесены по языкам в отдельные файлы (Faq_En, Faq_Ru, Faq_Zh,
 * Faq_Ar). Здесь только модели и точка входа `groupsFor(language)`.
 *
 * Переводы на ZH/AR — best-effort. Если ты добавишь обновления —
 * редактируй соответствующий FaqXX.kt файл.
 */
object Faq {

    data class Group(val title: String, val items: List<Item>)
    data class Item(val question: String, val answer: String)

    /** Группы для текущего языка (с фоллбеком на английский). */
    fun groupsFor(language: AppLanguage): List<Group> = when (language) {
        AppLanguage.RU -> FaqRu
        AppLanguage.ZH -> FaqZh
        AppLanguage.AR -> FaqAr
        else           -> FaqEn
    }

    // Хелперы — чтобы не повторять @-handle во всех текстах.
    internal const val BOT      = LegalDocs.BOT_HANDLE
    internal const val SUPPORT  = LegalDocs.SUPPORT_HANDLE
    internal const val SUPPORT_LINK = LegalDocs.SUPPORT_LINK
}
