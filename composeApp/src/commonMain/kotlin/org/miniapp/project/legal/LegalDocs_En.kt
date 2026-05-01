package org.miniapp.project.legal

private const val BOT      = LegalDocs.BOT_HANDLE
private const val SUPPORT  = LegalDocs.SUPPORT_HANDLE
private const val SUPPORT_LINK = LegalDocs.SUPPORT_LINK
private const val LAST_UPDATED = LegalDocs.LAST_UPDATED

internal val LegalDocsEn: Map<LegalDocs.Doc, String> = mapOf(
    LegalDocs.Doc.PRIVACY to """
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
$BOT. We are **not** a mobile network operator. Connectivity is
provided by upstream eSIM service providers and their respective
network partners in each country.

## 2. What this notice covers

This notice explains what personal data we receive when you use
eSIMobile, why we keep it, and your rights regarding it.

## 3. What we collect and how

We may receive the following data:

- Telegram user ID, first name, last name, username, language code —
  provided by the Telegram Mini App platform.
- ICCIDs of eSIMs you purchase, and the country/plan you bought.
- Payment metadata (transaction id, amount, status) — provided by the
  payment processor, never the card or wallet credentials themselves.
- Technical data such as platform and request timestamps — used for
  debugging and abuse prevention.

We **do not** ask for your email, phone number, address or any other
personal details.

## 4. How we use your data

We use this data only to:

- authenticate you within the app;
- show you the eSIMs you have purchased;
- process refunds or support requests;
- detect and prevent abuse of the service.

We do not use your data for advertising, profiling, or marketing. We
do not sell or share your data with third parties for marketing.

## 5. How long we keep your data

We keep order records for as long as necessary to support you and your
eSIMs. You may ask us to delete your records by contacting $SUPPORT.

## 6. Where your data is stored

The application backend is hosted in a European data centre. The
upstream eSIM provider may process minimal data on its own
infrastructure, which may be located outside the European Economic
Area. Telegram-specific data is stored by Telegram according to its
own privacy policy (https://telegram.org/privacy).

## 7. Sharing your data

We share data with third parties only to the extent strictly necessary
to deliver the service:

- the upstream eSIM provider, to issue your eSIM;
- the payment provider, to take payment;
- Telegram, which inherently sees your interactions with the bot.

## 8. Your rights

You can ask us to show you, correct or delete your data. Send any such
request to $SUPPORT. We will reply within 30 days.

## 9. Contact

For any privacy question or request: $SUPPORT ($SUPPORT_LINK).

This Privacy Policy was last updated on $LAST_UPDATED.
""".trimIndent(),

    LegalDocs.Doc.TERMS to """
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
offer. The contract is formed only when we accept the order and
deliver the eSIM activation details.

## 3. Your responsibility — device compatibility

It is **your responsibility** to make sure your device supports eSIM
and that an eSIM plan can be lawfully used in the destination country.
If you buy an eSIM for an incompatible device, you will not be
entitled to a refund.

## 4. No warranty on connectivity

We provide no warranty or guarantee on availability, quality, speed,
coverage or security of mobile connectivity. Mobile connectivity is
delivered by third-party network operators and may fail for reasons
beyond our control.

## 5. Prohibited use

You must not use the service for any unlawful purpose, to send spam,
harass others, infringe IP rights, or violate the upstream provider's
acceptable-use policy or telecommunications regulations.

## 6. Price and payment

Prices are shown in the Mini App. Bank or wallet fees that your
payment method may add are on you.

## 7. Cancellation and refunds

You may cancel an order **before** payment is confirmed. After we have
delivered the eSIM activation details, the order cannot be cancelled
and you will not be entitled to a refund — including in cases where
the eSIM does not work because of device incompatibility, lack of
coverage, or upstream provider issues.

If we cannot deliver an eSIM you have paid for (e.g. provider
outage), we will refund the amount paid within 14 days.

## 8. Liability

To the fullest extent permitted by law, our **total aggregate
liability** for any claim is limited to the amount you paid for the
affected order in the month before the claim. We are **not** liable
for indirect, consequential or business losses.

## 9. Force majeure

We are not liable for any delay or failure caused by events outside
our reasonable control.

## 10. Changes to these terms

We may update these terms from time to time. The latest version is
always visible inside the Mini App.

## 11. Governing law

These terms are governed by the law of the user's country of
residence, to the extent that mandatory consumer-protection rules
apply. Otherwise, disputes will be resolved in good faith using the
contact channel below.

## 12. Contact

Support and disputes: $SUPPORT ($SUPPORT_LINK).

These Terms and Conditions were last updated on $LAST_UPDATED.
""".trimIndent(),

    LegalDocs.Doc.COMPLAINTS to """
# Complaints Policy

## 1. Purpose

eSIMobile is a **private project**. We don't have a corporate
complaints department, but we want to hear if something went wrong
and fix it where we can.

## 2. What this covers

You can file a complaint about the quality of service or support, a
delay or defect in eSIM delivery, or the behaviour of the operator of
eSIMobile. General questions and data-protection requests should be
sent through the regular support channel.

## 3. How to file

Send a message to $SUPPORT ($SUPPORT_LINK). Please include:

- your Telegram username;
- the order ID, ICCID or transaction reference, if any;
- a clear description of what went wrong;
- screenshots or other evidence;
- what outcome you would like.

Complaints should be filed within **5 calendar days** of the
incident.

## 4. How we handle it

- We will acknowledge your complaint within 10 calendar days.
- We aim to resolve complaints within 30 calendar days. Complex cases
  may take longer; we'll keep you updated.
- At the end of the process we will share our findings and any
  action taken.

## 5. Confidentiality

Complaints are treated confidentially.

This Complaints Policy was last updated on $LAST_UPDATED.
""".trimIndent(),

    LegalDocs.Doc.BUG_BOUNTY to """
# Bug Bounty Program

## 1. About

eSIMobile is a small private project. We don't have a paid bug-bounty
budget, but we welcome reports from security researchers and we're
happy to credit anyone who helps improve the security of the service.

## 2. In scope

- The eSIMobile Mini App (frontend and backend) at the production
  domain.
- The Telegram bot $BOT.
- Authentication, authorisation, payment and order-fulfilment flows.

## 3. Out of scope

- Vulnerabilities in Telegram itself, or in upstream providers.
- Third-party services we depend on.
- UI/UX issues with no security impact.
- DDoS, social engineering, physical attacks.

## 4. Rules of engagement

- Test only against your own Telegram account / your own data.
- Don't read, modify or destroy other users' data.
- Don't run automated scans that could affect availability.
- Give us a reasonable opportunity to fix issues before public
  disclosure (suggested 90 days).

## 5. How to report

Send a private message to $SUPPORT ($SUPPORT_LINK) with:

- a clear description of the vulnerability;
- step-by-step reproduction;
- the impact;
- any logs, screenshots, or PoC.

We will reply within 7 calendar days.

## 6. Recognition

If you report a valid issue you'd like to be credited for, we'll add
your handle to a "Security thanks" list once the issue is fixed. We
currently cannot offer cash rewards.

## 7. Safe harbour

We will not pursue legal action against researchers who follow this
policy in good faith.

This program description was last updated on $LAST_UPDATED.
""".trimIndent(),
)
