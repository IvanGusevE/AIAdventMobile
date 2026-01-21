package ru.aiadvent.mobile.presentation.chat

import ru.aiadvent.mobile.domain.model.ChatInteraction
import ru.aiadvent.mobile.domain.model.MistralModel
import ru.aiadvent.mobile.domain.prompt.PromptType
import ru.aiadvent.mobile.presentation.chat.model.ChatUiMessage

data class State(
    val messages: List<ChatUiMessage> = listOf(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val selectedPromptType: PromptType = PromptType.GENERAL_ASSISTANT,
    val isPromptMenuExpanded: Boolean = false,
    val paramsDialog: ChatParamsDialog? = null,
    val isInteractionsDialogOpen: Boolean = false,
    val interactions: List<ChatInteraction> = emptyList()
)

data class ChatParamsDialog(
    val systemPrompt: String,
    val temperature: Double,
    val model: MistralModel
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
        val model: MistralModel
    ) : Event

    data object OnClearChatClick : Event
    data object OnInteractionsDialogOpen : Event
    data object OnInteractionsDialogDismiss : Event
}

sealed interface Effect {
    data class ShowError(val message: String) : Effect
}
