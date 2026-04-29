package org.miniapp.project.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    EN("en", "English", "🇬🇧"),
    RU("ru", "Русский", "🇷🇺"),
    ZH("zh", "中文",     "🇨🇳"),
    AR("ar", "العربية",  "🇸🇦");

    val isRtl: Boolean get() = this == AR

    companion object {
        fun fromCode(code: String?): AppLanguage = when (code?.lowercase()?.take(2)) {
            "ru" -> RU
            "zh" -> ZH
            "ar" -> AR
            else -> EN
        }
    }
}

/** Все строки приложения в одном месте. Один `data class`, 4 экземпляра. */
data class Strings(
    // Bottom nav
    val tabCatalog: String,
    val tabMyEsims: String,
    val tabProfile: String,

    // Catalog
    val catalogTitle: String,
    val catalogSearchHint: String,
    val catalogEmpty: String,
    val errorPrefix: String,
    val durationDays: String,                   // "{n} days" — подставим n через replace

    // Bundle detail
    val countriesLabel: String,
    val speedLabel: String,
    val buy: String,
    val paymentPlaceholder: String,
    val confirmOrder: String,
    val plan: String,
    val data: String,
    val duration: String,
    val price: String,
    val confirm: String,
    val cancel: String,
    val paymentStub: String,
    val back: String,

    // My eSIMs
    val myEsimsTitle: String,
    val myEsimsEmpty: String,
    val statusLabel: String,
    val activationCodeLabel: String,
    val smdpLabel: String,
    val esim: String,
    val iccid: String,

    // Profile
    val profileTitle: String,
    val authFailedTitle: String,
    val anonymous: String,
    val telegramId: String,
    val languageLabel: String,
    val notInTelegram: String,
    val supportTitle: String,
    val supportText: String,
    val chooseLanguage: String,

    // Theme switcher
    val themeLabel: String,
    val themeLight: String,
    val themeDark: String,
    val themeSystem: String,

    // Catalog regions
    val regionAll: String,
    val regionPopular: String,
    val regionEurope: String,
    val regionAsia: String,
    val regionAmericas: String,
    val regionAfrica: String,
    val regionMiddleEast: String,
    val regionOceania: String,
    val regionGlobal: String,

    // Country list
    val plansCount: String,                     // "{n} plans"
    val fromPrice: String,                      // "from {price}"
    val countriesSearch: String,                // search hint

    // Country screen
    val priceFilter: String,
    val priceAny: String,
    val priceUnder5: String,
    val price5to15: String,
    val price15plus: String,
    val plansForCountry: String,                // "Plans for {country}"

    // Payment
    val continueToPayment: String,
    val choosePayment: String,
    val payTelegramStars: String,
    val payCard: String,
    val payCrypto: String,
    val paymentSoon: String,                    // "Coming soon"

    // Smart filters
    val filtersTitle: String,
    val filterReset: String,
    val dataLabel: String,
    val dataUnder1: String,
    val data1to5: String,
    val data5to15: String,
    val data15plus: String,
    val dataUnlimited: String,
    val durationLabel: String,
    val durationUnder7: String,
    val duration8to30: String,
    val duration30plus: String,
    val sortLabel: String,
    val sortCheapest: String,
    val sortMoreData: String,
    val sortLonger: String,

    // Help / Install eSIM
    val helpTitle: String,
    val howToInstallTitle: String,
    val howToInstallSubtitle: String,
    val installStep1: String,
    val installStep2: String,
    val installStep3: String,
    val installStep4: String,
    val installNote: String,
    val checkCompatTitle: String,
    val checkCompatIntro: String,
    val checkCompatIos: String,
    val checkCompatAndroid: String,
    val checkCompatList: String,
    val openHelp: String,

    // Legal & FAQ
    val legalSection: String,                    // "Legal & FAQ"
    val openLegal: String,                       // ссылка из ProfileScreen
    val legalFaq: String,
    val legalFaqSubtitle: String,
    val legalPrivacy: String,
    val legalPrivacySubtitle: String,
    val legalTerms: String,
    val legalTermsSubtitle: String,
    val legalComplaints: String,
    val legalComplaintsSubtitle: String,
    val legalBugBounty: String,
    val legalBugBountySubtitle: String,
    val legalDisclaimer: String,                 // мелкий текст внизу

    // Misc
    val unlimited: String,
    val loading: String,
)

private val EnStrings = Strings(
    tabCatalog = "Catalog",
    tabMyEsims = "My eSIMs",
    tabProfile = "Profile",

    catalogTitle = "eSIM catalog",
    catalogSearchHint = "Country ISO (US, GB, TR…)",
    catalogEmpty = "Nothing found",
    errorPrefix = "Error:",
    durationDays = "{n} d",

    countriesLabel = "Countries",
    speedLabel = "Speed",
    buy = "Buy",
    paymentPlaceholder = "Payment is connected separately",
    confirmOrder = "Confirm order",
    plan = "Plan",
    data = "Data",
    duration = "Duration",
    price = "Price",
    confirm = "Confirm",
    cancel = "Cancel",
    paymentStub = "💳 Payment: stub (will be added later).",
    back = "Back",

    myEsimsTitle = "My eSIMs",
    myEsimsEmpty = "Your eSIMs will appear here after purchase.",
    statusLabel = "Status",
    activationCodeLabel = "Activation code",
    smdpLabel = "SM-DP+",
    esim = "eSIM",
    iccid = "ICCID",

    profileTitle = "Profile",
    authFailedTitle = "Sign-in failed",
    anonymous = "Anonymous",
    telegramId = "Telegram ID",
    languageLabel = "Language",
    notInTelegram = "Running outside Telegram — sign-in unavailable.",
    supportTitle = "Support",
    supportText = "For any questions, message us at @esimobilehelp.",
    chooseLanguage = "Choose language",

    themeLabel = "Theme",
    themeLight = "Light",
    themeDark = "Dark",
    themeSystem = "System",

    regionAll = "All",
    regionPopular = "Popular",
    regionEurope = "Europe",
    regionAsia = "Asia",
    regionAmericas = "Americas",
    regionAfrica = "Africa",
    regionMiddleEast = "Middle East",
    regionOceania = "Oceania",
    regionGlobal = "Global",

    plansCount = "{n} plans",
    fromPrice = "from {price}",
    countriesSearch = "Search country",

    priceFilter = "Price",
    priceAny = "Any",
    priceUnder5 = "≤ $5",
    price5to15 = "$5–15",
    price15plus = "$15+",
    plansForCountry = "Plans for {country}",

    continueToPayment = "Continue to payment",
    choosePayment = "Choose payment method",
    payTelegramStars = "Telegram Stars",
    payCard = "Bank card",
    payCrypto = "Crypto",
    paymentSoon = "Coming soon",

    filtersTitle = "Filters",
    filterReset = "Reset",
    dataLabel = "Data",
    dataUnder1 = "< 1 GB",
    data1to5 = "1–5 GB",
    data5to15 = "5–15 GB",
    data15plus = "15 GB+",
    dataUnlimited = "Unlimited",
    durationLabel = "Duration",
    durationUnder7 = "≤ 7 d",
    duration8to30 = "8–30 d",
    duration30plus = "30 d+",
    sortLabel = "Sort",
    sortCheapest = "Cheapest",
    sortMoreData = "More data",
    sortLonger = "Longer",

    helpTitle = "Help",
    howToInstallTitle = "How to install eSIM",
    howToInstallSubtitle = "After purchase you'll see a QR code and an activation code in the My eSIMs section.",
    installStep1 = "Open Settings on your phone.",
    installStep2 = "iPhone: Cellular → Add Cellular Plan. Android: Network & Internet → SIMs → Add eSIM.",
    installStep3 = "Choose \"Use QR Code\" and scan it from another screen, or pick \"Enter Manually\" and paste the activation code.",
    installStep4 = "Name the new line (e.g. Travel) and turn on Data Roaming for it. Done.",
    installNote = "Install while on Wi-Fi at home — once installed, an eSIM cannot be moved between devices.",
    checkCompatTitle = "Does my phone support eSIM?",
    checkCompatIntro = "Quick way to check on your phone:",
    checkCompatIos = "iPhone: Settings → General → About → look for IMEI and EID. If EID is present, eSIM is supported.",
    checkCompatAndroid = "Android: Settings → About phone → Status → SIM status / EID. Or dial *#06# — if EID is shown, eSIM is supported.",
    checkCompatList = "Most iPhones from XS (2018) and modern Pixel / Galaxy / Xiaomi flagships support eSIM. US iPhones (14 and later) are eSIM-only.",
    openHelp = "How to install eSIM",

    legalSection = "Legal & FAQ",
    openLegal = "Legal & FAQ",
    legalFaq = "Frequently asked questions",
    legalFaqSubtitle = "eSIM basics, installation, troubleshooting",
    legalPrivacy = "Privacy Policy",
    legalPrivacySubtitle = "What data we collect and why",
    legalTerms = "Terms and Conditions",
    legalTermsSubtitle = "The rules of using eSIMobile",
    legalComplaints = "Complaints Policy",
    legalComplaintsSubtitle = "How to file a complaint",
    legalBugBounty = "Bug Bounty Program",
    legalBugBountySubtitle = "Help us keep the service safe",
    legalDisclaimer = "eSIMobile is a private, non-commercial project. " +
        "All documents are in English only.",

    unlimited = "∞ Unlimited",
    loading = "Loading…",
)

private val RuStrings = Strings(
    tabCatalog = "Каталог",
    tabMyEsims = "Мои eSIM",
    tabProfile = "Профиль",

    catalogTitle = "Каталог eSIM",
    catalogSearchHint = "ISO страны (US, GB, TR…)",
    catalogEmpty = "Ничего не найдено",
    errorPrefix = "Ошибка:",
    durationDays = "{n} дн.",

    countriesLabel = "Страны",
    speedLabel = "Скорость",
    buy = "Купить",
    paymentPlaceholder = "Оплата подключается отдельно",
    confirmOrder = "Подтвердить заказ",
    plan = "Тариф",
    data = "Объём",
    duration = "Срок",
    price = "Цена",
    confirm = "Подтвердить",
    cancel = "Отмена",
    paymentStub = "💳 Оплата: заглушка (подключим позже).",
    back = "Назад",

    myEsimsTitle = "Мои eSIM",
    myEsimsEmpty = "Здесь появятся ваши eSIM после покупки.",
    statusLabel = "Статус",
    activationCodeLabel = "Код активации",
    smdpLabel = "SM-DP+",
    esim = "eSIM",
    iccid = "ICCID",

    profileTitle = "Профиль",
    authFailedTitle = "Не удалось войти",
    anonymous = "Аноним",
    telegramId = "Telegram ID",
    languageLabel = "Язык",
    notInTelegram = "Запущено вне Telegram — вход недоступен.",
    supportTitle = "Поддержка",
    supportText = "По любым вопросам пишите в Telegram: @esimobilehelp.",
    chooseLanguage = "Выберите язык",

    themeLabel = "Тема",
    themeLight = "Светлая",
    themeDark = "Тёмная",
    themeSystem = "Системная",

    regionAll = "Все",
    regionPopular = "Популярные",
    regionEurope = "Европа",
    regionAsia = "Азия",
    regionAmericas = "Америка",
    regionAfrica = "Африка",
    regionMiddleEast = "Ближний Восток",
    regionOceania = "Океания",
    regionGlobal = "Глобальные",

    plansCount = "{n} тарифов",
    fromPrice = "от {price}",
    countriesSearch = "Поиск страны",

    priceFilter = "Цена",
    priceAny = "Любая",
    priceUnder5 = "≤ $5",
    price5to15 = "$5–15",
    price15plus = "$15+",
    plansForCountry = "Тарифы · {country}",

    continueToPayment = "Перейти к оплате",
    choosePayment = "Способ оплаты",
    payTelegramStars = "Telegram Stars",
    payCard = "Банковская карта",
    payCrypto = "Криптовалюта",
    paymentSoon = "Скоро",

    filtersTitle = "Фильтры",
    filterReset = "Сбросить",
    dataLabel = "Объём",
    dataUnder1 = "< 1 ГБ",
    data1to5 = "1–5 ГБ",
    data5to15 = "5–15 ГБ",
    data15plus = "15 ГБ+",
    dataUnlimited = "Безлимит",
    durationLabel = "Срок",
    durationUnder7 = "≤ 7 дн.",
    duration8to30 = "8–30 дн.",
    duration30plus = "30 дн.+",
    sortLabel = "Сортировка",
    sortCheapest = "Дешевле",
    sortMoreData = "Больше ГБ",
    sortLonger = "Дольше",

    helpTitle = "Помощь",
    howToInstallTitle = "Как установить eSIM",
    howToInstallSubtitle = "После покупки в разделе «Мои eSIM» появится QR-код и код активации.",
    installStep1 = "Откройте Настройки на телефоне.",
    installStep2 = "iPhone: «Сотовая связь» → «Добавить тарифный план». Android: «Сеть и интернет» → «SIM-карты» → «Добавить eSIM».",
    installStep3 = "Выберите «Использовать QR» и отсканируйте его с другого экрана, либо «Ввести вручную» — вставьте код активации.",
    installStep4 = "Назовите новую линию (например, «Travel») и включите для неё передачу данных в роуминге. Готово.",
    installNote = "Лучше устанавливайте по Wi-Fi дома — после установки eSIM невозможно перенести на другое устройство.",
    checkCompatTitle = "Поддерживает ли мой телефон eSIM?",
    checkCompatIntro = "Быстрая проверка на телефоне:",
    checkCompatIos = "iPhone: Настройки → Основные → Об этом устройстве → IMEI и EID. Если есть EID, eSIM поддерживается.",
    checkCompatAndroid = "Android: Настройки → О телефоне → Статус → SIM / EID. Или наберите *#06# — если показан EID, eSIM поддерживается.",
    checkCompatList = "Большинство iPhone начиная с XS (2018) и современные Pixel / Galaxy / Xiaomi топовых линеек поддерживают eSIM. iPhone 14+ для США — только eSIM.",
    openHelp = "Как установить eSIM",

    legalSection = "Документы и FAQ",
    openLegal = "Документы и FAQ",
    legalFaq = "Частые вопросы",
    legalFaqSubtitle = "Об eSIM, установка, неполадки",
    legalPrivacy = "Privacy Policy",
    legalPrivacySubtitle = "Какие данные собираем и зачем",
    legalTerms = "Terms and Conditions",
    legalTermsSubtitle = "Правила использования eSIMobile",
    legalComplaints = "Complaints Policy",
    legalComplaintsSubtitle = "Как подать жалобу",
    legalBugBounty = "Bug Bounty Program",
    legalBugBountySubtitle = "Помогите сделать сервис безопаснее",
    legalDisclaimer = "eSIMobile — частный некоммерческий проект. " +
        "Сами документы доступны только на английском.",

    unlimited = "∞ Безлимит",
    loading = "Загрузка…",
)

private val ZhStrings = Strings(
    tabCatalog = "目录",
    tabMyEsims = "我的 eSIM",
    tabProfile = "个人资料",

    catalogTitle = "eSIM 目录",
    catalogSearchHint = "国家代码 (US, GB, TR…)",
    catalogEmpty = "未找到结果",
    errorPrefix = "错误：",
    durationDays = "{n} 天",

    countriesLabel = "国家",
    speedLabel = "速度",
    buy = "购买",
    paymentPlaceholder = "支付将单独接入",
    confirmOrder = "确认订单",
    plan = "套餐",
    data = "流量",
    duration = "时长",
    price = "价格",
    confirm = "确认",
    cancel = "取消",
    paymentStub = "💳 支付：占位（稍后接入）。",
    back = "返回",

    myEsimsTitle = "我的 eSIM",
    myEsimsEmpty = "购买后您的 eSIM 将显示在此处。",
    statusLabel = "状态",
    activationCodeLabel = "激活码",
    smdpLabel = "SM-DP+",
    esim = "eSIM",
    iccid = "ICCID",

    profileTitle = "个人资料",
    authFailedTitle = "登录失败",
    anonymous = "匿名",
    telegramId = "Telegram ID",
    languageLabel = "语言",
    notInTelegram = "在 Telegram 之外运行 — 无法登录。",
    supportTitle = "支持",
    supportText = "如有任何问题，请通过 Telegram 联系 @esimobilehelp。",
    chooseLanguage = "选择语言",

    themeLabel = "主题",
    themeLight = "浅色",
    themeDark = "深色",
    themeSystem = "跟随系统",

    regionAll = "全部",
    regionPopular = "热门",
    regionEurope = "欧洲",
    regionAsia = "亚洲",
    regionAmericas = "美洲",
    regionAfrica = "非洲",
    regionMiddleEast = "中东",
    regionOceania = "大洋洲",
    regionGlobal = "全球",

    plansCount = "{n} 个套餐",
    fromPrice = "起价 {price}",
    countriesSearch = "搜索国家",

    priceFilter = "价格",
    priceAny = "全部",
    priceUnder5 = "≤ $5",
    price5to15 = "$5–15",
    price15plus = "$15+",
    plansForCountry = "{country} 套餐",

    continueToPayment = "继续支付",
    choosePayment = "选择支付方式",
    payTelegramStars = "Telegram Stars",
    payCard = "银行卡",
    payCrypto = "加密货币",
    paymentSoon = "即将推出",

    filtersTitle = "筛选",
    filterReset = "重置",
    dataLabel = "流量",
    dataUnder1 = "< 1 GB",
    data1to5 = "1–5 GB",
    data5to15 = "5–15 GB",
    data15plus = "15 GB+",
    dataUnlimited = "无限",
    durationLabel = "时长",
    durationUnder7 = "≤ 7 天",
    duration8to30 = "8–30 天",
    duration30plus = "30 天+",
    sortLabel = "排序",
    sortCheapest = "最便宜",
    sortMoreData = "更多流量",
    sortLonger = "更长时间",

    helpTitle = "帮助",
    howToInstallTitle = "如何安装 eSIM",
    howToInstallSubtitle = "购买后，在「我的 eSIM」中会显示二维码和激活码。",
    installStep1 = "打开手机的「设置」。",
    installStep2 = "iPhone：蜂窝网络 → 添加蜂窝网络方案。Android：网络与互联网 → SIM 卡 → 添加 eSIM。",
    installStep3 = "选择「使用二维码」从另一个屏幕扫描，或选择「手动输入」并粘贴激活码。",
    installStep4 = "为新线路命名（例如 Travel），开启数据漫游。完成。",
    installNote = "建议在家中通过 Wi-Fi 安装。一旦安装，eSIM 无法在设备之间转移。",
    checkCompatTitle = "我的手机支持 eSIM 吗？",
    checkCompatIntro = "在手机上快速检查：",
    checkCompatIos = "iPhone：设置 → 通用 → 关于本机 → 查看 IMEI 和 EID。若有 EID，则支持 eSIM。",
    checkCompatAndroid = "Android：设置 → 关于手机 → 状态信息 → SIM / EID。或拨号 *#06#，若显示 EID 则支持 eSIM。",
    checkCompatList = "iPhone XS（2018）及以后机型、Pixel / Galaxy / Xiaomi 高端旗舰大多支持 eSIM。美版 iPhone 14 起仅支持 eSIM。",
    openHelp = "如何安装 eSIM",

    legalSection = "条款和常见问题",
    openLegal = "条款和常见问题",
    legalFaq = "常见问题",
    legalFaqSubtitle = "eSIM 基础、安装、故障排查",
    legalPrivacy = "Privacy Policy",
    legalPrivacySubtitle = "我们收集哪些数据以及为什么收集",
    legalTerms = "Terms and Conditions",
    legalTermsSubtitle = "使用 eSIMobile 的规则",
    legalComplaints = "Complaints Policy",
    legalComplaintsSubtitle = "如何提出投诉",
    legalBugBounty = "Bug Bounty Program",
    legalBugBountySubtitle = "协助提升服务安全性",
    legalDisclaimer = "eSIMobile 是一个非商业的私人项目。" +
        "所有文档仅提供英文版。",

    unlimited = "∞ 无限",
    loading = "加载中…",
)

private val ArStrings = Strings(
    tabCatalog = "الكتالوج",
    tabMyEsims = "بطاقاتي",
    tabProfile = "الملف الشخصي",

    catalogTitle = "كتالوج eSIM",
    catalogSearchHint = "رمز الدولة (US, GB, TR…)",
    catalogEmpty = "لم يتم العثور على شيء",
    errorPrefix = "خطأ:",
    durationDays = "{n} يوم",

    countriesLabel = "الدول",
    speedLabel = "السرعة",
    buy = "شراء",
    paymentPlaceholder = "سيتم ربط الدفع بشكل منفصل",
    confirmOrder = "تأكيد الطلب",
    plan = "الباقة",
    data = "البيانات",
    duration = "المدة",
    price = "السعر",
    confirm = "تأكيد",
    cancel = "إلغاء",
    paymentStub = "💳 الدفع: مؤقت (سيُربط لاحقًا).",
    back = "رجوع",

    myEsimsTitle = "بطاقاتي",
    myEsimsEmpty = "ستظهر بطاقات eSIM الخاصة بك هنا بعد الشراء.",
    statusLabel = "الحالة",
    activationCodeLabel = "رمز التفعيل",
    smdpLabel = "SM-DP+",
    esim = "eSIM",
    iccid = "ICCID",

    profileTitle = "الملف الشخصي",
    authFailedTitle = "فشل تسجيل الدخول",
    anonymous = "مجهول",
    telegramId = "Telegram ID",
    languageLabel = "اللغة",
    notInTelegram = "يعمل خارج Telegram — تسجيل الدخول غير متاح.",
    supportTitle = "الدعم",
    supportText = "لأي استفسار، راسلنا على Telegram: ‎@esimobilehelp.",
    chooseLanguage = "اختر اللغة",

    themeLabel = "السمة",
    themeLight = "فاتحة",
    themeDark = "داكنة",
    themeSystem = "النظام",

    regionAll = "الكل",
    regionPopular = "الأكثر شعبية",
    regionEurope = "أوروبا",
    regionAsia = "آسيا",
    regionAmericas = "الأمريكتان",
    regionAfrica = "أفريقيا",
    regionMiddleEast = "الشرق الأوسط",
    regionOceania = "أوقيانوسيا",
    regionGlobal = "عالمي",

    plansCount = "{n} باقات",
    fromPrice = "ابتداءً من {price}",
    countriesSearch = "ابحث عن دولة",

    priceFilter = "السعر",
    priceAny = "الكل",
    priceUnder5 = "≤ $5",
    price5to15 = "$5–15",
    price15plus = "$15+",
    plansForCountry = "باقات {country}",

    continueToPayment = "متابعة إلى الدفع",
    choosePayment = "اختر طريقة الدفع",
    payTelegramStars = "Telegram Stars",
    payCard = "بطاقة مصرفية",
    payCrypto = "عملة مشفرة",
    paymentSoon = "قريبًا",

    filtersTitle = "الفلاتر",
    filterReset = "إعادة ضبط",
    dataLabel = "البيانات",
    dataUnder1 = "< 1 GB",
    data1to5 = "1–5 GB",
    data5to15 = "5–15 GB",
    data15plus = "15 GB+",
    dataUnlimited = "غير محدود",
    durationLabel = "المدة",
    durationUnder7 = "≤ 7 أيام",
    duration8to30 = "8–30 يوم",
    duration30plus = "+30 يوم",
    sortLabel = "ترتيب",
    sortCheapest = "الأرخص",
    sortMoreData = "بيانات أكثر",
    sortLonger = "مدة أطول",

    helpTitle = "مساعدة",
    howToInstallTitle = "كيفية تثبيت eSIM",
    howToInstallSubtitle = "بعد الشراء، ستظهر في «بطاقاتي» رمز QR ورمز التفعيل.",
    installStep1 = "افتح «الإعدادات» على الهاتف.",
    installStep2 = "iPhone: «شبكة الجوال» → «إضافة خطة شبكة جوال». Android: «الشبكة والإنترنت» → «بطاقات SIM» → «إضافة eSIM».",
    installStep3 = "اختر «استخدم رمز QR» وامسحه من شاشة أخرى، أو «إدخال يدوي» وألصق رمز التفعيل.",
    installStep4 = "سَمِّ الخط الجديد (مثلاً Travel) وفعّل تجوال البيانات له. تم.",
    installNote = "يفضّل التثبيت عبر Wi-Fi في المنزل. لا يمكن نقل eSIM بين الأجهزة بعد التثبيت.",
    checkCompatTitle = "هل يدعم هاتفي eSIM؟",
    checkCompatIntro = "تحقق سريع على الهاتف:",
    checkCompatIos = "iPhone: الإعدادات → عام → حول → IMEI و EID. إذا ظهر EID، فإن eSIM مدعوم.",
    checkCompatAndroid = "Android: الإعدادات → حول الهاتف → الحالة → SIM / EID. أو اطلب *#06# — إن ظهر EID فإن eSIM مدعوم.",
    checkCompatList = "معظم iPhone من XS (2018) فما فوق، و Pixel / Galaxy / Xiaomi الحديثة تدعم eSIM. iPhone 14+ في الولايات المتحدة يستخدم eSIM فقط.",
    openHelp = "كيفية تثبيت eSIM",

    legalSection = "المستندات والأسئلة الشائعة",
    openLegal = "المستندات والأسئلة الشائعة",
    legalFaq = "الأسئلة الشائعة",
    legalFaqSubtitle = "أساسيات eSIM، التثبيت، استكشاف الأخطاء",
    legalPrivacy = "Privacy Policy",
    legalPrivacySubtitle = "البيانات التي نجمعها ولماذا",
    legalTerms = "Terms and Conditions",
    legalTermsSubtitle = "قواعد استخدام eSIMobile",
    legalComplaints = "Complaints Policy",
    legalComplaintsSubtitle = "كيفية تقديم شكوى",
    legalBugBounty = "Bug Bounty Program",
    legalBugBountySubtitle = "ساعدنا في الحفاظ على أمان الخدمة",
    legalDisclaimer = "eSIMobile مشروع خاص غير تجاري. " +
        "المستندات متاحة باللغة الإنجليزية فقط.",

    unlimited = "∞ غير محدود",
    loading = "جارٍ التحميل…",
)

fun stringsFor(language: AppLanguage): Strings = when (language) {
    AppLanguage.EN -> EnStrings
    AppLanguage.RU -> RuStrings
    AppLanguage.ZH -> ZhStrings
    AppLanguage.AR -> ArStrings
}

val LocalStrings = staticCompositionLocalOf { EnStrings }
val LocalAppLanguage = compositionLocalOf { AppLanguage.EN }

/** Оборачивает контент в текущий язык + правильное направление RTL/LTR. */
@Composable
fun LocaleProvider(language: AppLanguage, content: @Composable () -> Unit) {
    val direction = if (language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    CompositionLocalProvider(
        LocalAppLanguage provides language,
        LocalStrings provides stringsFor(language),
        LocalLayoutDirection provides direction,
    ) { content() }
}
