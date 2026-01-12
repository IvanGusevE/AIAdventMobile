package ru.aiadvent.mobile.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageRole {
    @SerialName("user")
    USER,
    @SerialName("assistant")
    ASSISTANT,
    @SerialName("system")
    SYSTEM
}
