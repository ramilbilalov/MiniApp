package org.miniapp.project.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val initData: String)

@Serializable
data class AuthResponse(val token: String, val user: TelegramUser)

@Serializable
data class GuestAuthRequest(val guestId: String? = null)

@Serializable
data class GuestAuthResponse(
    val token: String,
    val guestId: String,
    val user: TelegramUser,
)

@Serializable
data class TelegramUser(
    val id: Long,
    val first_name: String?,
    val last_name: String? = null,
    val username: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("language_code") val languageCode: String? = null,
)

@Serializable
data class CatalogueResponse(
    val bundles: List<Bundle>,
    val pageSize: Int = 0,
    val totalCount: Int = 0,
)

@Serializable
data class Bundle(
    val name: String,
    val description: String? = null,
    val groups: List<String> = emptyList(),
    val countries: List<Country> = emptyList(),
    val dataAmount: Long? = null,
    val duration: Int? = null,
    val speed: List<String> = emptyList(),
    val autostart: Boolean? = null,
    val unlimited: Boolean = false,
    val price: Double = 0.0,
    val currency: String = "USD",
    val imageUrl: String? = null,
    /** Источник тарифа: "esimgo" / "esimcard". В UI не показываем. */
    val source: String = "esimgo",
)

@Serializable
data class Country(
    val name: String? = null,
    val iso: String? = null,
    val region: String? = null,
)

@Serializable
data class CreateOrderRequest(
    val bundleName: String,
    val quantity: Int = 1,
    val paymentToken: String? = null,
)

@Serializable
data class CheckoutRequest(
    val bundleName: String,
    val quantity: Int = 1,
    /** "rollypay" | "stars" */
    val gateway: String,
)

@Serializable
data class CheckoutResponse(
    val orderId: String,
    val payUrl: String,
    val gateway: String,
    val expiresAt: Long? = null,
)

@Serializable
data class OrderStatusResponse(
    val orderId: String,
    /** "pending" | "paid" | "failed" | "expired" | "refunded" */
    val status: String,
    val esim: EsimInfo? = null,
)

@Serializable
data class CreateOrderResponse(
    val orderReference: String,
    val status: String,
    val total: Double,
    val currency: String,
    val esims: List<EsimInfo> = emptyList(),
)

@Serializable
data class EsimInfo(
    val iccid: String,
    val matchingId: String? = null,
    val smdpAddress: String? = null,
    val activationCode: String? = null,
    val qrCodeUrl: String? = null,
    val bundleName: String? = null,
    val status: String? = null,
)

@Serializable
data class MyEsimsResponse(val esims: List<EsimInfo>)

@Serializable
data class ErrorResponse(val error: String, val details: String? = null)
