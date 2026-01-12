package ru.aiadvent.mobile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val role: MessageRole,
    val content: String
)
