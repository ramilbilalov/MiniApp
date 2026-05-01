package org.miniapp.project.legal

import org.miniapp.project.ui.i18n.AppLanguage

/**
 * Юридические документы приложения.
 *
 * eSIMobile — **частный (некоммерческий) проект**, не зарегистрированное
 * юридическое лицо.
 *
 * Тексты разнесены по языкам: LegalDocs_En, LegalDocs_Ru, LegalDocs_Zh,
 * LegalDocs_Ar. Английская версия — авторитетная (так указано в начале
 * каждой не-английской версии).
 *
 * Markdown-подобный синтаксис (см. `DocumentScreen`):
 *   `# Title`        — заголовок 1 уровня
 *   `## Subtitle`    — заголовок 2 уровня
 *   `- bullet`       — пункт списка
 *   обычный текст   — параграф
 *   пустая строка   — разделитель
 */
object LegalDocs {

    enum class Doc {
        PRIVACY,
        TERMS,
        COMPLAINTS,
        BUG_BOUNTY,
    }

    fun titleFor(doc: Doc, language: AppLanguage): String = when (language) {
        AppLanguage.RU -> when (doc) {
            Doc.PRIVACY    -> "Политика конфиденциальности"
            Doc.TERMS      -> "Пользовательское соглашение"
            Doc.COMPLAINTS -> "Политика жалоб"
            Doc.BUG_BOUNTY -> "Программа Bug Bounty"
        }
        AppLanguage.ZH -> when (doc) {
            Doc.PRIVACY    -> "隐私政策"
            Doc.TERMS      -> "条款与条件"
            Doc.COMPLAINTS -> "投诉政策"
            Doc.BUG_BOUNTY -> "漏洞赏金计划"
        }
        AppLanguage.AR -> when (doc) {
            Doc.PRIVACY    -> "سياسة الخصوصية"
            Doc.TERMS      -> "الشروط والأحكام"
            Doc.COMPLAINTS -> "سياسة الشكاوى"
            Doc.BUG_BOUNTY -> "برنامج مكافأة الثغرات"
        }
        else -> when (doc) {
            Doc.PRIVACY    -> "Privacy Policy"
            Doc.TERMS      -> "Terms and Conditions"
            Doc.COMPLAINTS -> "Complaints Policy"
            Doc.BUG_BOUNTY -> "Bug Bounty Program"
        }
    }

    fun textFor(doc: Doc, language: AppLanguage): String {
        val map = when (language) {
            AppLanguage.RU -> LegalDocsRu
            AppLanguage.ZH -> LegalDocsZh
            AppLanguage.AR -> LegalDocsAr
            else           -> LegalDocsEn
        }
        return map[doc] ?: LegalDocsEn[doc].orEmpty()
    }

    const val PROJECT_NAME       = "eSIMobile"
    const val SUPPORT_HANDLE     = "@esimobilehelp"
    const val BOT_HANDLE         = "@esimobile"
    const val SUPPORT_LINK       = "https://t.me/esimobilehelp"
    const val BOT_LINK           = "https://t.me/esimobile"
    const val LAST_UPDATED       = "April 2026"
}
