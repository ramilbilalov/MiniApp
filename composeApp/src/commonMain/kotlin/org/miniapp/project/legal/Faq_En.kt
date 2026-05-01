package org.miniapp.project.legal

internal val FaqEn: List<Faq.Group> = listOf(
    Faq.Group(
        title = "About eSIM",
        items = listOf(
            Faq.Item(
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
            Faq.Item(
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
            Faq.Item(
                question = "Does my phone support eSIM?",
                answer = """
Most modern flagship devices do. Quick checks:

**iPhone**
1. Settings → Cellular → SIMs. If you see "Add eSIM", your iPhone
   supports it.
2. Settings → General → About → look for the EID number. If EID is
   present, eSIM is supported.
3. Make sure your iPhone is **not carrier-locked**.

**Android**
1. Dial `*#06#`. If you see an EID, your phone supports eSIM.
2. Or open Settings → Connections / Network & Internet → SIM
   Manager. If you see "Add eSIM" or "Download a SIM", it's supported.
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "Buying & installing",
        items = listOf(
            Faq.Item(
                question = "How do I buy an eSIM?",
                answer = """
1. Open eSIMobile inside Telegram (bot ${Faq.BOT}) or via the web.
2. Pick the country or region you're travelling to.
3. Choose a plan (data, duration, price).
4. Pay using one of the available methods.
5. After payment, the QR code and activation code appear in
   **My eSIMs**.
""".trim()
            ),
            Faq.Item(
                question = "Where do I find my QR code?",
                answer = """
Open eSIMobile, tap **My eSIMs** in the bottom bar (or open it from
your profile). Tap the plan card — the QR code and activation details
will be shown.
""".trim()
            ),
            Faq.Item(
                question = "How to install an eSIM on iPhone",
                answer = """
1. Open eSIMobile and go to **My eSIMs**, tap your plan.
2. Make sure you're connected to a stable Wi-Fi network.
3. Tap **Install** to add the eSIM to your device.
4. If you're already in the destination country, enable the plan:
   Settings → Cellular → choose the eSIM → turn on **Data Roaming**.
""".trim()
            ),
            Faq.Item(
                question = "How to install an eSIM on Android",
                answer = """
1. Open eSIMobile and go to **My eSIMs**, tap your plan.
2. Make sure you have a stable Wi-Fi connection.
3. Point your camera at the QR code (or pick the QR image from your
   gallery if your device supports it). Follow the on-screen prompts.
4. When in the destination country, enable Mobile Data, choose the
   eSIM and turn on **Data roaming**.
""".trim()
            ),
            Faq.Item(
                question = "How do I check remaining data?",
                answer = """
Open **My eSIMs**, tap your plan. The screen shows remaining data and
days left.

Note: after the first connection it can take up to one hour for the
first usage update; after that, data refreshes every ~30 minutes.
""".trim()
            ),
            Faq.Item(
                question = "Where do I find the ICCID of my eSIM?",
                answer = """
ICCID is the unique identifier of your eSIM, often requested by
support to investigate issues. Open **My eSIMs**, tap your plan — the
ICCID is shown on the plan detail screen.
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "Troubleshooting",
        items = listOf(
            Faq.Item(
                question = "“This QR code is no longer valid”",
                answer = """
Possible reasons:

- The eSIM is **already installed** on your phone.
- The QR code wasn't scanned cleanly — try again with better lighting.
- Your internet was unstable during installation, or VPN was on. Turn
  the VPN off and connect to stable Wi-Fi.
- You have too many eSIMs already installed (>10 on most devices).

If the eSIM was previously installed and then **deleted**, it usually
can't be reinstalled. Contact ${Faq.SUPPORT}.
""".trim()
            ),
            Faq.Item(
                question = "eSIM activation failed on iPhone",
                answer = """
**If you're not yet in the destination country:**
Nothing to do. The eSIM activates automatically once you arrive. Don't
delete it.

**If you're already in the destination country:**
Check that:

- Cellular Data is set to the eSIM.
- Data Roaming is on for that eSIM.
- Voice & Data is set to LTE or 5G Auto.

Then turn off Wi-Fi and reboot the phone. If it still doesn't connect,
contact ${Faq.SUPPORT} with screenshots.
""".trim()
            ),
            Faq.Item(
                question = "I have no internet on my eSIM",
                answer = """
1. **Basics:** turn on Data Roaming, reboot the phone, set network
   mode to LTE.
2. **Try selecting the network manually:** open network selection,
   turn off automatic mode, try the supported operators one by one.

If nothing helps, contact ${Faq.SUPPORT} with screenshots of your
Cellular settings, SIM list and APN.
""".trim()
            ),
            Faq.Item(
                question = "Hotspot / personal hotspot doesn't work",
                answer = """
Tethering depends on the local operator and your device, and we can't
guarantee it works everywhere.

**iOS:** Settings → Cellular → choose the eSIM → Cellular Data
Network. Check that the **Hotspot** APN matches the **Mobile Data**
APN. If different, copy the Mobile Data APN value into the Hotspot
field. Reboot.
""".trim()
            ),
            Faq.Item(
                question = "How to delete an eSIM",
                answer = """
**Important:** don't delete an eSIM as a troubleshooting step — most
eSIMs can be installed only once.

**Apple:** Settings → Cellular → tap the SIM → Delete eSIM.
**Google:** Settings → Network & Internet → Mobile Network → eSIM →
Delete SIM.
**Samsung:** Settings → Connections → SIM Manager → eSIM → Delete.
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "Account & data",
        items = listOf(
            Faq.Item(
                question = "How do I delete my account?",
                answer = """
1. Open eSIMobile.
2. Go to Profile → Delete account.
3. Read the deletion notice and confirm. Your account is queued for
   deletion within 14 days.
""".trim()
            ),
            Faq.Item(
                question = "How can I contact support?",
                answer = """
For any question, write to ${Faq.SUPPORT} (${Faq.SUPPORT_LINK}). Please
include your Telegram username and, if possible, the ICCID or order
id.
""".trim()
            ),
        )
    ),
)
