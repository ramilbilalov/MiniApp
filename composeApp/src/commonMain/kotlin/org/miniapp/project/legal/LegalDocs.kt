package org.miniapp.project.legal

/**
 * Юридические документы приложения.
 *
 * eSIMobile — это **частный (некоммерческий) проект**, не зарегистрированное
 * юридическое лицо. Тексты документов адаптированы под этот статус: вместо
 * корпоративных дисклеймеров используется явное предупреждение пользователя
 * о том, что услугу оказывает физическое лицо без регистрации компании.
 *
 * Тексты — только на английском. По стандартному markdown-подобному синтаксису:
 *   `# Title`        — заголовок 1 уровня
 *   `## Subtitle`    — заголовок 2 уровня
 *   `- bullet`       — пункт списка
 *   обычный текст   — параграф
 *   пустая строка   — разделитель
 */
object LegalDocs {

    enum class Doc(val titleEn: String) {
        PRIVACY("Privacy Policy"),
        TERMS("Terms and Conditions"),
        COMPLAINTS("Complaints Policy"),
        BUG_BOUNTY("Bug Bounty Program"),
    }

    fun textFor(doc: Doc): String = when (doc) {
        Doc.PRIVACY     -> PRIVACY_POLICY
        Doc.TERMS       -> TERMS_AND_CONDITIONS
        Doc.COMPLAINTS  -> COMPLAINTS_POLICY
        Doc.BUG_BOUNTY  -> BUG_BOUNTY_PROGRAM
    }

    const val PROJECT_NAME       = "eSIMobile"
    const val SUPPORT_HANDLE     = "@esimobilehelp"
    const val BOT_HANDLE         = "@esimobile"
    const val SUPPORT_LINK       = "https://t.me/esimobilehelp"
    const val BOT_LINK           = "https://t.me/esimobile"
    const val LAST_UPDATED       = "April 2026"

    private val PRIVACY_POLICY = """
# Privacy Policy

## Important notice — private project

eSIMobile is a **private, non-commercial project** run by an individual,
not a registered business entity. There is no legal company behind this
service. By using eSIMobile you acknowledge and accept that the service
is provided "as is", without the consumer guarantees that would apply
to a registered business in your jurisdiction.

We respect your privacy and only collect the minimum data required to
provide the service.

## 1. Who runs eSIMobile

eSIMobile is operated as a personal project by an individual. The
service is delivered through a Telegram Mini App accessible via the bot
$BOT_HANDLE. We are **not** a mobile network operator. Connectivity is
provided by upstream eSIM service providers and their respective
network partners in each country.

We do not assume responsibility for connectivity, its quality, coverage,
security or any other feature you may expect from a licensed mobile
operator.

## 2. What this notice covers

This notice explains what personal data we receive when you use
eSIMobile, why we keep it, and your rights regarding it.

## 3. What is personal data

"Personal data" means any information that can be used to identify you,
either on its own or combined with other information. This includes
obvious identifiers like your name, but also things like your Telegram
user ID and IP address.

## 4. What we collect and how

We may receive the following data when you use the Mini App:

- Telegram user ID, first name, last name, username, language code —
  provided to us by the Telegram Mini App platform when you open the
  app, via Telegram's `initData` mechanism.
- ICCIDs of eSIMs you purchase, and the country/plan you bought —
  required to fulfill orders and let you see them later in "My eSIMs".
- Payment metadata (transaction id, amount, status) — provided by the
  payment processor, never the card or wallet credentials themselves.
- Technical data such as the platform you opened the Mini App from
  (web / iOS / Android) and approximate request timestamps — used for
  debugging and abuse prevention.

We **do not** ask for your email, phone number, address or any other
personal details.

## 5. How we use your data

We use this data only to:

- authenticate you within the app (verifying Telegram's signature);
- show you the eSIMs you have purchased;
- process refunds or support requests if you ask us to;
- detect and prevent abuse of the service.

We do not use your data for advertising, profiling, or marketing. We do
not sell or share your data with third parties for marketing purposes.

## 6. How long we keep your data

We keep order records (Telegram ID, ICCID, plan, timestamp) for as long
as necessary to support you and your eSIMs, and to comply with any
applicable legal obligations. You may ask us to delete your records by
contacting $SUPPORT_HANDLE.

## 7. Where your data is stored

The application backend is hosted in a European data centre. The
upstream eSIM provider may process minimal data (country, plan, ICCID)
on its own infrastructure, which may be located outside the European
Economic Area.

Telegram-specific data (your profile, messages with the bot) is stored
by Telegram according to its own privacy policy
(https://telegram.org/privacy).

## 8. Sharing your data

We share data with third parties only to the extent strictly necessary
to deliver the service:

- the upstream eSIM provider, to issue your eSIM;
- the payment provider, to take payment;
- Telegram, which inherently sees your interactions with the bot.

## 9. Your rights

You can ask us to:

- show you what data we have about you;
- correct it if it's wrong;
- delete it.

Send any such request to $SUPPORT_HANDLE. We will reply within 30 days.

## 10. Contact

For any privacy question or request: $SUPPORT_HANDLE
($SUPPORT_LINK).

## 11. Changes

We may update this notice from time to time, for example if the law
changes or if we change how the service works. The latest version will
always be visible inside the Mini App.

This Privacy Policy was last updated on $LAST_UPDATED.
""".trim()

    private val TERMS_AND_CONDITIONS = """
# Terms and Conditions

## Important notice — private project

eSIMobile is a **private, non-commercial project** operated by an
individual, **not a registered business entity**. By using eSIMobile
you acknowledge that no consumer-protection laws applicable to
registered businesses cover this service, and that you accept the
service "as is".

If you do not agree with these terms, do not use the service.

## 1. About eSIMobile

eSIMobile is a Telegram Mini App that resells data-only eSIM plans
provided by upstream eSIM providers. We do **not** own or operate any
telecommunications infrastructure. We do **not** issue eSIMs ourselves.

## 2. The contract

When you place an order through the Mini App, you make a contractual
offer. The contract is formed only when we accept the order and deliver
the eSIM activation details (QR code / activation code). We may decline
any order at our discretion, in which case any payment will be
refunded.

## 3. Your responsibility — device compatibility

It is **your responsibility** to make sure your device supports eSIM
and that an eSIM plan can be lawfully used in the destination country.
We provide a non-exhaustive list of compatible devices and best-effort
guidance, but we do not guarantee its accuracy. If you buy an eSIM for
a device that does not support eSIM, you will not be entitled to a
refund.

## 4. No warranty on connectivity

We provide no warranty or guarantee on:

- availability, quality, speed, coverage or security of mobile
  connectivity;
- compatibility with any specific device, network or country;
- continuity of service from upstream providers.

You expressly accept that mobile connectivity is delivered by
third-party network operators and may fail for reasons beyond our
control.

## 5. Prohibited use

You must not use the service:

- for any unlawful purpose, or to facilitate any unlawful activity;
- to send spam, harass, defame or harm others;
- to infringe anyone's intellectual property rights;
- to circumvent network security or the upstream provider's
  acceptable-use policy;
- in violation of telecommunications regulations of any country.

We may suspend or terminate your access without prior notice if you
breach these terms.

## 6. Price and payment

Prices are shown in the Mini App at the moment of order. All prices
include any applicable taxes if those are charged by us or our
processors. Bank or wallet fees that your payment method may add are
on you.

## 7. Cancellation and refunds

You may cancel an order **before** payment is confirmed. After we have
delivered the eSIM activation details, the order cannot be cancelled
and you will not be entitled to a refund — including in cases where
the eSIM does not work because of device incompatibility, lack of
coverage in your area, or upstream provider issues we cannot control.

If we cannot deliver an eSIM you have paid for (e.g. provider outage),
we will refund the amount paid within 14 days, using the same payment
method.

## 8. Liability

To the fullest extent permitted by law, our **total aggregate
liability** for any claim arising from the service is limited to the
amount you paid for the affected order in the month before the claim.
We are **not** liable for indirect, consequential, business or
opportunity losses.

## 9. Force majeure

We are not liable for any delay or failure caused by events outside
our reasonable control, including but not limited to: power or
internet outages, strikes, pandemics, natural disasters, government
action, war, or upstream provider failures.

## 10. Changes to these terms

We may update these terms from time to time. The latest version is
always visible inside the Mini App. If you continue to use the service
after a change, you accept the updated terms.

## 11. Governing law

These terms are governed by the law of the user's country of
residence, to the extent that mandatory consumer-protection rules
apply. Otherwise, disputes will be resolved in good faith between you
and the operator of eSIMobile, using the contact channel below.

## 12. Contact

Support and disputes: $SUPPORT_HANDLE ($SUPPORT_LINK).

These Terms and Conditions were last updated on $LAST_UPDATED.
""".trim()

    private val COMPLAINTS_POLICY = """
# Complaints Policy

## 1. Purpose

eSIMobile is a **private project**. We don't have a corporate
complaints department, but we want to hear if something went wrong and
fix it where we can.

## 2. What this covers

You can file a complaint about:

- the quality of the service or support you received;
- a delay or defect in eSIM delivery;
- the behaviour of the operator of eSIMobile.

The following are **not** complaints under this policy and should
instead be sent as regular questions:

- general questions about how eSIMs work;
- requests for information disclosure under data-protection law (use
  the Privacy Policy contact instead).

## 3. How to file

Send an email or Telegram message to $SUPPORT_HANDLE
($SUPPORT_LINK). Please include:

- your Telegram username;
- the order ID, ICCID or transaction reference, if any;
- a clear description of what went wrong;
- screenshots or other evidence;
- what outcome you would like.

Complaints should be filed within **5 calendar days** of the incident.
Complaints filed after that may be rejected.

We may reject complaints that are abusive, threatening, racist or
otherwise offensive.

## 4. How we handle it

- We will acknowledge your complaint within 10 calendar days. If we
  need more information, we'll ask within the same window.
- We aim to resolve complaints within 30 calendar days. Complex cases
  may take longer; we'll keep you updated.
- At the end of the process we will share our findings and any action
  taken.

## 5. Confidentiality

Complaints are treated confidentially. We may use anonymised details
to improve the service.

This Complaints Policy was last updated on $LAST_UPDATED.
""".trim()

    private val BUG_BOUNTY_PROGRAM = """
# Bug Bounty Program

## 1. About

eSIMobile is a small private project. We don't have a paid bug-bounty
budget, but we welcome reports from security researchers and we're
happy to credit anyone who helps us improve the security of the
service.

## 2. Scope — in scope

- The eSIMobile Mini App (frontend and backend) at the production
  domain.
- The Telegram bot $BOT_HANDLE.
- Authentication, authorisation, payment and order-fulfilment flows.

## 3. Out of scope

- Vulnerabilities in Telegram itself, or in the upstream eSIM
  provider's systems.
- Third-party services we depend on (TimeWeb hosting, payment
  processors, TonAPI, etc.) — please report those to the respective
  providers.
- UI/UX issues with no security impact.
- DDoS, social engineering, physical attacks.

## 4. Rules of engagement

- Test only against your own Telegram account / your own data.
- Don't read, modify or destroy other users' data.
- Don't run automated scans that could affect availability.
- Give us a reasonable opportunity to fix issues before public
  disclosure (suggested 90 days).

## 5. How to report

Send a private message to $SUPPORT_HANDLE ($SUPPORT_LINK) with:

- a clear description of the vulnerability;
- step-by-step reproduction;
- the impact (what an attacker could achieve);
- any logs, screenshots, or proof-of-concept code.

We will reply within 7 calendar days.

## 6. Recognition

If you report a valid issue you'd like to be credited for, we'll add
your handle to a "Security thanks" list inside the app once the issue
is fixed. We currently cannot offer cash rewards.

## 7. Safe harbour

We will not pursue legal action against researchers who follow this
policy in good faith.

This program description was last updated on $LAST_UPDATED.
""".trim()
}
