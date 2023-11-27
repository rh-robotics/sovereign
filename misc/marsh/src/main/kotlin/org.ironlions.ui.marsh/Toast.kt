package org.ironlions.ui.marsh

data class ToastClassification(
    val classification: String,
    val red: Int,
    val green: Int,
    val blue: Int,
)

open class Toast(val classification: ToastClassification, val body: String) {
    private var exception: Exception? = null
    var timeToLive = 5f

    class Info(body: String) : Toast(ToastClassification("Info", 255, 255, 255), body)
    class Warning(body: String) : Toast(ToastClassification("Warning", 252, 163, 17), body)
    class Error(body: String) : Toast(ToastClassification("Error", 255, 0, 0), body)
    class Custom(classification: ToastClassification, body: String) : Toast(classification, body)

    fun setTimeToLive(timeToLive: Float) = apply { this.timeToLive = timeToLive }
    fun setException(exception: Exception) = apply { this.exception = exception }
    fun formatted(): String = "$body${exception?.let { "\n\nReason: $it" } ?: ""}"
}