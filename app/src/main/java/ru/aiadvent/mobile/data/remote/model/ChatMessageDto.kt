package ru.aiadvent.mobile.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val role: MessageRoleDto,
    val content: String
)
