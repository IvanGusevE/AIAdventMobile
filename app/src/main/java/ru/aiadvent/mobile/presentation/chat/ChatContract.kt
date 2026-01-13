package ru.aiadvent.mobile.presentation.chat

import ru.aiadvent.mobile.presentation.chat.model.ChatUiMessage

data class State(
    val messages: List<ChatUiMessage> = listOf(),
    val inputText: String = "",
    val isLoading: Boolean = false,
)

sealed interface Event {
    data class OnInputChanged(val text: String) : Event
    data object OnSendClick : Event
}

sealed interface Effect {
    data class ShowError(val message: String) : Effect
}
