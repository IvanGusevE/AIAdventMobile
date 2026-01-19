package ru.aiadvent.mobile.presentation.chat

import ru.aiadvent.mobile.domain.prompt.PromptType
import ru.aiadvent.mobile.presentation.chat.model.ChatUiMessage

data class State(
    val messages: List<ChatUiMessage> = listOf(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val selectedPromptType: PromptType = PromptType.GENERAL_ASSISTANT,
    val isPromptMenuExpanded: Boolean = false,
    val paramsDialog: ChatParamsDialog? = null
)

data class ChatParamsDialog(
    val systemPrompt: String,
    val temperature: Double
)

sealed interface Event {
    data class OnInputChanged(val text: String) : Event
    data object OnSendClick : Event
    data object OnPromptMenuClick : Event
    data object OnPromptMenuDismiss : Event
    data class OnPromptTypeSelected(val promptType: PromptType) : Event
    data object OnChatParamsDialogOpen : Event
    data object OnChatParamsDialogDismiss : Event
    data class OnChatParamsDialogApply(
        val systemPrompt: String,
        val temperature: Double,
    ) : Event

    data object OnClearChatClick : Event
}

sealed interface Effect {
    data class ShowError(val message: String) : Effect
}
