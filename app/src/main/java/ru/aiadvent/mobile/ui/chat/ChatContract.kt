package ru.aiadvent.mobile.ui.chat

import ru.aiadvent.mobile.data.model.MessageRole

data class ChatUiMessage(
    val id: String,
    val content: String,
    val role: MessageRole,
    val timestamp: Long = System.currentTimeMillis()
)

data class ChatState(
    val messages: List<ChatUiMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
)

sealed interface ChatEvent {
    data class OnInputChanged(val text: String) : ChatEvent
    data object OnSendClick : ChatEvent
}

sealed interface ChatEffect {
    data class ShowError(val message: String) : ChatEffect
}
