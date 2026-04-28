package org.miniapp.project.ui

import org.miniapp.project.ui.i18n.AppLanguage

/**
 * Локализованные названия стран по ISO-2.
 * Используется для отображения и для **поиска**: при вводе пользователь
 * совпадает с любым из языковых вариантов.
 */
object CountryNames {

    private val ru = mapOf(
        "AD" to "Андорра", "AE" to "ОАЭ", "AF" to "Афганистан", "AG" to "Антигуа и Барбуда",
        "AL" to "Албания", "AM" to "Армения", "AO" to "Ангола", "AR" to "Аргентина",
        "AT" to "Австрия", "AU" to "Австралия", "AZ" to "Азербайджан", "BA" to "Босния и Герцеговина",
        "BB" to "Барбадос", "BD" to "Бангладеш", "BE" to "Бельгия", "BF" to "Буркина-Фасо",
        "BG" to "Болгария", "BH" to "Бахрейн", "BI" to "Бурунди", "BJ" to "Бенин",
        "BN" to "Бруней", "BO" to "Боливия", "BR" to "Бразилия", "BS" to "Багамы",
        "BT" to "Бутан", "BW" to "Ботсвана", "BY" to "Беларусь", "BZ" to "Белиз",
        "CA" to "Канада", "CD" to "ДР Конго", "CF" to "ЦАР", "CG" to "Конго",
        "CH" to "Швейцария", "CI" to "Кот-д'Ивуар", "CL" to "Чили", "CM" to "Камерун",
        "CN" to "Китай", "CO" to "Колумбия", "CR" to "Коста-Рика", "CU" to "Куба",
        "CV" to "Кабо-Верде", "CY" to "Кипр", "CZ" to "Чехия", "DE" to "Германия",
        "DJ" to "Джибути", "DK" to "Дания", "DM" to "Доминика", "DO" to "Доминикана",
        "DZ" to "Алжир", "EC" to "Эквадор", "EE" to "Эстония", "EG" to "Египет",
        "ER" to "Эритрея", "ES" to "Испания", "ET" to "Эфиопия", "FI" to "Финляндия",
        "FJ" to "Фиджи", "FR" to "Франция", "GA" to "Габон", "GB" to "Великобритания",
        "GE" to "Грузия", "GH" to "Гана", "GM" to "Гамбия", "GN" to "Гвинея",
        "GR" to "Греция", "GT" to "Гватемала", "GW" to "Гвинея-Бисау", "GY" to "Гайана",
        "HK" to "Гонконг", "HN" to "Гондурас", "HR" to "Хорватия", "HT" to "Гаити",
        "HU" to "Венгрия", "ID" to "Индонезия", "IE" to "Ирландия", "IL" to "Израиль",
        "IN" to "Индия", "IQ" to "Ирак", "IR" to "Иран", "IS" to "Исландия",
        "IT" to "Италия", "JM" to "Ямайка", "JO" to "Иордания", "JP" to "Япония",
        "KE" to "Кения", "KG" to "Киргизия", "KH" to "Камбоджа", "KR" to "Южная Корея",
        "KW" to "Кувейт", "KZ" to "Казахстан", "LA" to "Лаос", "LB" to "Ливан",
        "LI" to "Лихтенштейн", "LK" to "Шри-Ланка", "LR" to "Либерия", "LS" to "Лесото",
        "LT" to "Литва", "LU" to "Люксембург", "LV" to "Латвия", "LY" to "Ливия",
        "MA" to "Марокко", "MC" to "Монако", "MD" to "Молдова", "ME" to "Черногория",
        "MG" to "Мадагаскар", "MK" to "Северная Македония", "ML" to "Мали", "MM" to "Мьянма",
        "MN" to "Монголия", "MO" to "Макао", "MR" to "Мавритания", "MT" to "Мальта",
        "MU" to "Маврикий", "MV" to "Мальдивы", "MW" to "Малави", "MX" to "Мексика",
        "MY" to "Малайзия", "MZ" to "Мозамбик", "NA" to "Намибия", "NE" to "Нигер",
        "NG" to "Нигерия", "NI" to "Никарагуа", "NL" to "Нидерланды", "NO" to "Норвегия",
        "NP" to "Непал", "NZ" to "Новая Зеландия", "OM" to "Оман", "PA" to "Панама",
        "PE" to "Перу", "PG" to "Папуа — Новая Гвинея", "PH" to "Филиппины", "PK" to "Пакистан",
        "PL" to "Польша", "PT" to "Португалия", "PY" to "Парагвай", "QA" to "Катар",
        "RO" to "Румыния", "RS" to "Сербия", "RU" to "Россия", "RW" to "Руанда",
        "SA" to "Саудовская Аравия", "SC" to "Сейшелы", "SD" to "Судан", "SE" to "Швеция",
        "SG" to "Сингапур", "SI" to "Словения", "SK" to "Словакия", "SL" to "Сьерра-Леоне",
        "SN" to "Сенегал", "SO" to "Сомали", "SR" to "Суринам", "SV" to "Сальвадор",
        "SY" to "Сирия", "SZ" to "Эсватини", "TD" to "Чад", "TG" to "Того",
        "TH" to "Таиланд", "TJ" to "Таджикистан", "TM" to "Туркменистан", "TN" to "Тунис",
        "TR" to "Турция", "TT" to "Тринидад и Тобаго", "TW" to "Тайвань", "TZ" to "Танзания",
        "UA" to "Украина", "UG" to "Уганда", "US" to "США", "UY" to "Уругвай",
        "UZ" to "Узбекистан", "VE" to "Венесуэла", "VN" to "Вьетнам", "YE" to "Йемен",
        "ZA" to "ЮАР", "ZM" to "Замбия", "ZW" to "Зимбабве",
    )

    private val zh = mapOf(
        "AE" to "阿联酋", "AR" to "阿根廷", "AT" to "奥地利", "AU" to "澳大利亚",
        "AZ" to "阿塞拜疆", "BD" to "孟加拉国", "BE" to "比利时", "BG" to "保加利亚",
        "BR" to "巴西", "BY" to "白俄罗斯", "CA" to "加拿大", "CH" to "瑞士",
        "CL" to "智利", "CN" to "中国", "CO" to "哥伦比亚", "CY" to "塞浦路斯",
        "CZ" to "捷克", "DE" to "德国", "DK" to "丹麦", "EE" to "爱沙尼亚",
        "EG" to "埃及", "ES" to "西班牙", "FI" to "芬兰", "FR" to "法国",
        "GB" to "英国", "GE" to "格鲁吉亚", "GR" to "希腊", "HK" to "香港",
        "HR" to "克罗地亚", "HU" to "匈牙利", "ID" to "印度尼西亚", "IE" to "爱尔兰",
        "IL" to "以色列", "IN" to "印度", "IS" to "冰岛", "IT" to "意大利",
        "JP" to "日本", "KH" to "柬埔寨", "KR" to "韩国", "KW" to "科威特",
        "KZ" to "哈萨克斯坦", "LT" to "立陶宛", "LU" to "卢森堡", "LV" to "拉脱维亚",
        "MA" to "摩洛哥", "MD" to "摩尔多瓦", "MO" to "澳门", "MX" to "墨西哥",
        "MY" to "马来西亚", "NL" to "荷兰", "NO" to "挪威", "NP" to "尼泊尔",
        "NZ" to "新西兰", "PH" to "菲律宾", "PK" to "巴基斯坦", "PL" to "波兰",
        "PT" to "葡萄牙", "QA" to "卡塔尔", "RO" to "罗马尼亚", "RS" to "塞尔维亚",
        "RU" to "俄罗斯", "SA" to "沙特阿拉伯", "SE" to "瑞典", "SG" to "新加坡",
        "SI" to "斯洛文尼亚", "SK" to "斯洛伐克", "TH" to "泰国", "TR" to "土耳其",
        "TW" to "台湾", "UA" to "乌克兰", "US" to "美国", "UZ" to "乌兹别克斯坦",
        "VN" to "越南", "ZA" to "南非",
    )

    private val ar = mapOf(
        "AE" to "الإمارات", "AR" to "الأرجنتين", "AT" to "النمسا", "AU" to "أستراليا",
        "BD" to "بنغلاديش", "BE" to "بلجيكا", "BG" to "بلغاريا", "BH" to "البحرين",
        "BR" to "البرازيل", "CA" to "كندا", "CH" to "سويسرا", "CN" to "الصين",
        "CY" to "قبرص", "CZ" to "التشيك", "DE" to "ألمانيا", "DK" to "الدنمارك",
        "DZ" to "الجزائر", "EG" to "مصر", "ES" to "إسبانيا", "FI" to "فنلندا",
        "FR" to "فرنسا", "GB" to "المملكة المتحدة", "GR" to "اليونان", "HK" to "هونغ كونغ",
        "HU" to "المجر", "ID" to "إندونيسيا", "IL" to "إسرائيل", "IN" to "الهند",
        "IQ" to "العراق", "IR" to "إيران", "IT" to "إيطاليا", "JO" to "الأردن",
        "JP" to "اليابان", "KR" to "كوريا الجنوبية", "KW" to "الكويت", "KZ" to "كازاخستان",
        "LB" to "لبنان", "LY" to "ليبيا", "MA" to "المغرب", "MX" to "المكسيك",
        "MY" to "ماليزيا", "NL" to "هولندا", "NO" to "النرويج", "NZ" to "نيوزيلندا",
        "OM" to "عُمان", "PH" to "الفلبين", "PK" to "باكستان", "PL" to "بولندا",
        "PT" to "البرتغال", "QA" to "قطر", "RO" to "رومانيا", "RU" to "روسيا",
        "SA" to "السعودية", "SE" to "السويد", "SG" to "سنغافورة", "SY" to "سوريا",
        "TH" to "تايلاند", "TN" to "تونس", "TR" to "تركيا", "UA" to "أوكرانيا",
        "US" to "الولايات المتحدة", "VN" to "فيتنام", "YE" to "اليمن", "ZA" to "جنوب أفريقيا",
    )

    /** Имя для отображения на текущем языке (фоллбэк — английское). */
    fun displayName(iso: String, language: AppLanguage, fallbackEnglish: String?): String {
        val key = iso.uppercase()
        val translated = when (language) {
            AppLanguage.RU -> ru[key]
            AppLanguage.ZH -> zh[key]
            AppLanguage.AR -> ar[key]
            AppLanguage.EN -> null
        }
        return translated ?: fallbackEnglish ?: key
    }

    /** Все известные имена страны, по которым возможен поиск (en + ru + zh + ar + iso). */
    fun searchableNames(iso: String, fallbackEnglish: String?): List<String> {
        val key = iso.uppercase()
        return listOfNotNull(
            key,
            fallbackEnglish,
            ru[key],
            zh[key],
            ar[key],
        )
    }
}
