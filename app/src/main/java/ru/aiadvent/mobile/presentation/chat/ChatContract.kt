package ru.aiadvent.mobile.presentation.chat

import ru.aiadvent.mobile.domain.prompt.PromptType
import ru.aiadvent.mobile.presentation.chat.model.ChatUiMessage

data class State(
    val messages: List<ChatUiMessage> = listOf(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val selectedPromptType: PromptType = PromptType.GENERAL_ASSISTANT,
    val isPromptMenuExpanded: Boolean = false,
    val customSystemPrompt: String = "",
    val isSystemPromptDialogVisible: Boolean = false,
)

sealed interface Event {
    data class OnInputChanged(val text: String) : Event
    data object OnSendClick : Event
    data object OnPromptMenuClick : Event
    data object OnPromptMenuDismiss : Event
    data class OnPromptTypeSelected(val promptType: PromptType) : Event
    data object OnSystemPromptDialogOpen : Event
    data object OnSystemPromptDialogDismiss : Event
    data class OnSystemPromptChanged(val prompt: String) : Event
    data object OnSystemPromptApply : Event
    data object OnSystemPromptClear : Event
}

sealed interface Effect {
    data class ShowError(val message: String) : Effect
}
