package ru.aiadvent.mobile.presentation.chat.model

import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole

data class ChatUiMessage(
    val content: String,
    val role: MessageRole,
    val timestamp: Long = System.currentTimeMillis()
)

fun Message.toUi(): ChatUiMessage = ChatUiMessage(
    content = content,
    role = role,
    timestamp = timestamp
)
