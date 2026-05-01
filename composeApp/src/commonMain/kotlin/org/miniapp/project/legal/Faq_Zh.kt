package org.miniapp.project.legal

internal val FaqZh: List<Faq.Group> = listOf(
    Faq.Group(
        title = "关于 eSIM",
        items = listOf(
            Faq.Item(
                question = "什么是 eSIM？我为什么需要它？",
                answer = """
eSIM（嵌入式 SIM 卡）是一种虚拟 SIM 卡，无需插入实体 SIM 即可
连接移动网络。

eSIMobile 销售**仅数据**的 eSIM 套餐，预付费上网包，便于旅行——
无需寻找当地 SIM 卡或支付漫游费。

**优点：**

- 快速安装——几分钟即可激活，无需访问营业厅。
- 比传统漫游更便宜。
- 多国家和地区可选。
- 与主 SIM 并行使用，无需移除原号码。
- 隐私——IP 地址来自 eSIM 发行国，而非您的本地网络。
""".trim()
            ),
            Faq.Item(
                question = "开始前的重要事项",
                answer = """
- 我们的 eSIM **仅供数据使用**。无电话号码。
- eSIM 仅在购买时指定的国家/地区有效。例如美国 eSIM 在意大利
  无法使用。
- 安装是**一次性**的。除非确认，否则不要删除已安装的 eSIM——
  可能无法重新安装。
- 如需更多流量，通常可以为同一 eSIM 续费。续费前请勿删除 eSIM。
- eSIM 不能在设备之间转移。
""".trim()
            ),
            Faq.Item(
                question = "我的手机支持 eSIM 吗？",
                answer = """
大多数现代旗舰设备都支持。快速检查：

**iPhone**
1. 设置 → 蜂窝网络 → SIM 卡。如果看到「添加 eSIM」，则支持。
2. 设置 → 通用 → 关于本机 → 查找 EID 编号。如有 EID，则支持 eSIM。
3. 确认 iPhone **未被运营商锁定**。

**Android**
1. 拨号 `*#06#`。如显示 EID，则支持 eSIM。
2. 或打开 设置 → 连接/网络与互联网 → SIM 卡管理器。如有
  「添加 eSIM」或「下载 SIM 卡」，则支持。
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "购买与安装",
        items = listOf(
            Faq.Item(
                question = "如何购买 eSIM？",
                answer = """
1. 在 Telegram 中打开 eSIMobile（机器人 ${Faq.BOT}）或通过网页。
2. 选择您要前往的国家或地区。
3. 选择套餐（流量、时长、价格）。
4. 使用可用的支付方式付款。
5. 付款后，二维码和激活码将出现在 **我的 eSIM** 中。
""".trim()
            ),
            Faq.Item(
                question = "在哪里找到我的二维码？",
                answer = """
打开 eSIMobile，在底部菜单点击 **我的 eSIM**（或从个人资料中
打开）。点击套餐卡片——将显示二维码和激活详情。
""".trim()
            ),
            Faq.Item(
                question = "如何在 iPhone 上安装 eSIM",
                answer = """
1. 打开 eSIMobile，进入 **我的 eSIM**，点击您的套餐。
2. 确保已连接到稳定的 Wi-Fi。
3. 点击 **安装** 将 eSIM 添加到设备。
4. 如果您已在目的地国家，请激活：设置 → 蜂窝网络 →
   选择 eSIM → 开启 **数据漫游**。
""".trim()
            ),
            Faq.Item(
                question = "如何在 Android 上安装 eSIM",
                answer = """
1. 打开 eSIMobile，进入 **我的 eSIM**，点击您的套餐。
2. 确保有稳定的 Wi-Fi 连接。
3. 将相机对准二维码（或从相册选择二维码图片）。按屏幕提示操作。
4. 抵达目的地国家后，启用移动数据，选择 eSIM，并开启 **数据漫游**。
""".trim()
            ),
            Faq.Item(
                question = "如何检查剩余流量？",
                answer = """
打开 **我的 eSIM**，点击您的套餐。屏幕将显示剩余流量和剩余天数。

注意：首次连接后约一小时内更新使用情况，之后每约 30 分钟刷新一次。
""".trim()
            ),
            Faq.Item(
                question = "在哪里找到 eSIM 的 ICCID？",
                answer = """
ICCID 是 eSIM 的唯一标识符，技术支持通常会要求提供。打开
**我的 eSIM**，点击套餐——ICCID 显示在套餐详情页面上。
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "故障排查",
        items = listOf(
            Faq.Item(
                question = "「此二维码不再有效」",
                answer = """
可能原因：

- eSIM **已安装**到您的手机上。
- 二维码扫描不清晰——尝试在更好的光线下重试。
- 安装时网络不稳定，或开启了 VPN。关闭 VPN，连接稳定的 Wi-Fi。
- 已安装的 eSIM 太多（大多数设备最多 10 个）。

如果 eSIM 已安装后又被**删除**，通常无法重新安装。请联系
${Faq.SUPPORT}。
""".trim()
            ),
            Faq.Item(
                question = "iPhone 上 eSIM 激活失败",
                answer = """
**如果您尚未抵达目的地国家：**
无需操作。抵达后 eSIM 将自动激活。请勿删除。

**如果您已在目的地国家：**
检查：

- 蜂窝数据已设置为 eSIM。
- 该 eSIM 已开启数据漫游。
- 语音与数据设置为 LTE 或 5G 自动。

然后关闭 Wi-Fi 并重启手机。如果仍无法连接，请联系 ${Faq.SUPPORT}
并附上截图。
""".trim()
            ),
            Faq.Item(
                question = "eSIM 上没有互联网",
                answer = """
1. **基础检查：** 开启数据漫游，重启手机，将网络模式设为 LTE。
2. **手动选择网络：** 进入网络选择，关闭自动模式，逐一尝试支持的
   运营商。

如果都无效，请联系 ${Faq.SUPPORT}，附上蜂窝设置、SIM 列表和
APN 设置的截图。
""".trim()
            ),
            Faq.Item(
                question = "热点 / 个人热点不工作",
                answer = """
共享网络取决于当地运营商和设备，我们无法保证在所有地方都能工作。

**iOS：** 设置 → 蜂窝网络 → 选择 eSIM → 蜂窝数据网络。检查
**热点** APN 是否与 **移动数据** APN 一致。如果不同，将移动数据
APN 的值复制到热点字段。重启手机。
""".trim()
            ),
            Faq.Item(
                question = "如何删除 eSIM",
                answer = """
**重要：** 请勿将删除 eSIM 作为故障排查步骤——大多数 eSIM 只能
安装一次。

**Apple：** 设置 → 蜂窝网络 → 点击 SIM → 删除 eSIM。
**Google：** 设置 → 网络与互联网 → 移动网络 → eSIM → 删除 SIM。
**Samsung：** 设置 → 连接 → SIM 卡管理器 → eSIM → 删除。
""".trim()
            ),
        )
    ),
    Faq.Group(
        title = "账户与数据",
        items = listOf(
            Faq.Item(
                question = "如何删除我的账户？",
                answer = """
1. 打开 eSIMobile。
2. 进入 个人资料 → 删除账户。
3. 阅读删除提示并确认。您的账户将在 14 天内排队删除。
""".trim()
            ),
            Faq.Item(
                question = "如何联系支持？",
                answer = """
任何问题请联系 ${Faq.SUPPORT}（${Faq.SUPPORT_LINK}）。请提供您的
Telegram 用户名，以及（如有）ICCID 或订单号。
""".trim()
            ),
        )
    ),
)
