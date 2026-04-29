package org.miniapp.project.legal

/**
 * Часто задаваемые вопросы (английский).
 *
 * Группированный список Q/A. Каждая группа отображается отдельной секцией,
 * каждый вопрос — отдельной expandable-карточкой.
 *
 * Тексты адаптированы под eSIMobile (без брендинга TonMobile).
 */
object Faq {

    data class Group(val title: String, val items: List<Item>)
    data class Item(val question: String, val answer: String)

    val groups: List<Group> = listOf(
        Group(
            title = "About eSIM",
            items = listOf(
                Item(
                    question = "What is an eSIM and why do I need one?",
                    answer = """
An eSIM (embedded SIM) is a virtual SIM card that lets you connect to a
mobile network without inserting a physical SIM card.

eSIMobile sells **data-only** eSIMs with prepaid internet packages.
Convenient when you travel and don't want to look for local SIM cards
or pay roaming fees.

**Benefits:**

- Fast setup — install the eSIM and activate the plan in minutes,
  no shop visit required.
- Better rates than traditional roaming.
- Wide coverage — plans for many countries and regions.
- Works alongside your main SIM. You don't have to remove your home
  number to use the travel eSIM.
- Privacy — you get an IP from the country where the eSIM was issued,
  not from your home network.
""".trim()
                ),
                Item(
                    question = "Important things before you start",
                    answer = """
- Our eSIMs are **data-only**. They give you internet access but no
  phone number.
- An eSIM works only in the country / region it was bought for. A US
  eSIM will not work in Italy.
- Installation is **one-shot**. Don't delete an installed eSIM unless
  you're sure — re-installation may not be possible.
- If you need more data, you can usually top up the same eSIM. Make
  sure the eSIM has not been deleted before topping up.
- An eSIM cannot be transferred between devices.
""".trim()
                ),
                Item(
                    question = "Does my phone support eSIM?",
                    answer = """
Most modern flagship devices do. Quick checks:

**iPhone**
1. Settings → Cellular → SIMs. If you see "Add eSIM", your iPhone
   supports it.
2. Settings → General → About → look for the EID number. If EID is
   present, eSIM is supported.
3. Make sure your iPhone is **not carrier-locked** (Settings →
   General → About → Carrier Lock should say "No SIM restrictions").

**Android**
1. Dial `*#06#`. If you see an EID, your phone supports eSIM.
2. Or open Settings → Connections / Network & Internet → SIM
   Manager. If you see "Add eSIM" or "Download a SIM", it's supported.

Note: iPhones produced for China, Hong Kong and Macao have two
physical SIM slots and **don't** support eSIM. Check Apple's regional
list at https://support.apple.com/en-us/108044.
""".trim()
                ),
            )
        ),
        Group(
            title = "Buying & installing",
            items = listOf(
                Item(
                    question = "How do I buy an eSIM?",
                    answer = """
1. Open eSIMobile inside Telegram (bot $BOT_HANDLE) or via the web.
2. Pick the country or region you're travelling to.
3. Choose a plan (data, duration, price).
4. Pay using one of the available methods.
5. After payment, the QR code and activation code appear in
   **My eSIMs**.
""".trim()
                ),
                Item(
                    question = "Where do I find my QR code?",
                    answer = """
Open eSIMobile, tap **My eSIMs** in the bottom bar (or open it from
your profile). Tap the plan card — the QR code and activation details
will be shown.
""".trim()
                ),
                Item(
                    question = "How to install an eSIM on iPhone",
                    answer = """
1. Open eSIMobile and go to **My eSIMs**, tap your plan.
2. Make sure you're connected to a stable Wi-Fi network.
3. Tap **Install** to add the eSIM to your device.
4. If you're already in the destination country, enable the plan:
   - Settings → Cellular → choose the eSIM for cellular data.
   - Turn off "Cellular Data Switching" if your main number is also on.
   - Tap the eSIM in the SIM list and turn on **Data Roaming**.
""".trim()
                ),
                Item(
                    question = "How to install an eSIM on Android",
                    answer = """
1. Open eSIMobile and go to **My eSIMs**, tap your plan.
2. Make sure you have a stable Wi-Fi connection.
3. Point your camera at the QR code (or pick the QR image from your
   gallery if your device supports it). Follow the on-screen prompts.
4. If a second device is not available, you can enter the activation
   code manually.
5. When in the destination country:
   - Enable Mobile Data (swipe down for the quick panel and tap the
     mobile-data icon).
   - Choose the eSIM for mobile data.
   - Turn on **Data roaming** (and disable your home line to avoid
     home-operator roaming fees).
""".trim()
                ),
                Item(
                    question = "How do I check remaining data?",
                    answer = """
Open **My eSIMs**, tap your plan. The screen shows remaining data and
days left.

Note: after the first connection it can take up to one hour for the
first usage update; after that, data refreshes every ~30 minutes.
""".trim()
                ),
                Item(
                    question = "Where do I find the ICCID of my eSIM?",
                    answer = """
ICCID is the unique identifier of your eSIM, often requested by
support to investigate issues.

Open **My eSIMs**, tap your plan — the ICCID is shown on the plan
detail screen.
""".trim()
                ),
            )
        ),
        Group(
            title = "Troubleshooting",
            items = listOf(
                Item(
                    question = "“This QR code is no longer valid”",
                    answer = """
Possible reasons:

- The eSIM is **already installed** on your phone (check your SIM
  list — you may not need to install it again).
- The QR code wasn't scanned cleanly — try again with better lighting.
- Your internet was unstable during installation, or VPN was on. Turn
  the VPN off and connect to stable Wi-Fi.
- You have too many eSIMs already installed (>10 on most devices,
  >5 on some). Remove unused eSIMs and try again.

If the eSIM was previously installed and then **deleted**, it usually
**can't be reinstalled**. Contact support: $SUPPORT_HANDLE.
""".trim()
                ),
                Item(
                    question = "eSIM activation failed on iPhone",
                    answer = """
**If you're not yet in the destination country:**
Nothing to do. The eSIM activates automatically once you arrive and
your phone connects to a supported network. Make sure:

- the eSIM appears in your SIM list;
- Data Roaming is on for that eSIM (Settings → Cellular → SIM →
  Data Roaming);
- you don't delete the eSIM (re-installation may not be possible).

**If you're already in the destination country:**
Check that:

- Cellular Data is set to the eSIM (Settings → Cellular → Cellular
  Data);
- Data Roaming is on for that eSIM;
- Voice & Data is set to LTE or 5G Auto.

Then turn off Wi-Fi and reboot the phone. If it still doesn't connect,
contact support with screenshots of your Cellular settings.
""".trim()
                ),
                Item(
                    question = "I have no internet on my eSIM",
                    answer = """
1. **Basics:**
   - Turn on Data Roaming.
   - Reboot the phone.
   - Set the network mode to LTE (Android: Network mode; iOS: Voice &
     Data → LTE).
2. **Try selecting the network manually:**
   - Open network selection.
   - Turn off automatic mode.
   - Wait a few seconds for the operator list to appear.
   - Try the supported operators one by one.
   - Toggle airplane mode to refresh the connection.

If nothing helps, contact $SUPPORT_HANDLE with screenshots:

- Cellular / Mobile Data settings showing the eSIM is selected.
- SIM list showing Data Roaming is on.
- APN settings.
""".trim()
                ),
                Item(
                    question = "Hotspot / personal hotspot doesn't work",
                    answer = """
Tethering depends on the local operator and your device, and we can't
guarantee it works everywhere.

**iOS:** Settings → Cellular → choose the eSIM → Cellular Data
Network. Check that the **Hotspot** APN matches the **Mobile Data**
APN. If it's empty or different, copy the Mobile Data APN value into
the Hotspot field. Reboot.
""".trim()
                ),
                Item(
                    question = "How to find which eSIM is which on my device",
                    answer = """
If you have several eSIMs with similar labels, you can identify the
right one by **ICCID** — the unique identifier shown in **My eSIMs**.

**iOS:** Settings → General → About → scroll to the SIM list. Find
the ICCID of the active eSIM. Compare it to the ICCID in the order.

**Android:** Settings → About phone → SIM card status. Or dial
`*#06#` for device info including ICCID.

Tip: when installing a new eSIM, give it a unique name (e.g. the
country) so it's easier to find later.
""".trim()
                ),
                Item(
                    question = "How to delete an eSIM",
                    answer = """
**Important:** don't delete an eSIM as a troubleshooting step — most
eSIMs can be installed only once.

**Apple:** Settings → Cellular / Mobile Data → tap the SIM → Delete
eSIM.

**Google:** Settings → Network & Internet → Mobile Network → select
the eSIM → Delete SIM.

**Samsung:** Settings → Connections → SIM Manager → eSIM → Delete
eSIM.
""".trim()
                ),
            )
        ),
        Group(
            title = "Account & data",
            items = listOf(
                Item(
                    question = "How do I delete my account?",
                    answer = """
1. Open eSIMobile.
2. Go to Profile → Delete account.
3. Read the deletion notice and tap **Delete account**, then confirm.
4. Your account is queued for deletion. Order history kept for legal
   reasons (if any) is anonymised within 14 days.
""".trim()
                ),
                Item(
                    question = "How can I contact support?",
                    answer = """
For any question or problem, write to $SUPPORT_HANDLE
($SUPPORT_LINK). Please include your Telegram username and, if
possible, the ICCID or order id.
""".trim()
                ),
            )
        ),
    )

    private const val BOT_HANDLE = LegalDocs.BOT_HANDLE
    private const val SUPPORT_HANDLE = LegalDocs.SUPPORT_HANDLE
    private const val SUPPORT_LINK = LegalDocs.SUPPORT_LINK
}
