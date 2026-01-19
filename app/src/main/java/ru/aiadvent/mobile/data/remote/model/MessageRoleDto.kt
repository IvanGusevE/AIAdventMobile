package ru.aiadvent.mobile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.aiadvent.mobile.domain.model.MessageRole

@Serializable
enum class MessageRoleDto {
    @SerialName("user")
    USER,

    @SerialName("assistant")
    ASSISTANT,

    @SerialName("system")
    SYSTEM
}

fun MessageRole.toRemote() = when (this) {
    MessageRole.USER -> MessageRoleDto.USER
    MessageRole.ASSISTANT -> MessageRoleDto.ASSISTANT
}
