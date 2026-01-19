package ru.aiadvent.mobile.presentation.chat

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.aiadvent.mobile.core.base.BaseViewModel
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.prompt.SystemPromptProvider
import ru.aiadvent.mobile.domain.usecase.GetSystemMessageUseCase
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendUserMessageUseCase
import ru.aiadvent.mobile.domain.usecase.UpdateSystemMessageUseCase
import ru.aiadvent.mobile.presentation.chat.Effect.ShowError
import ru.aiadvent.mobile.presentation.chat.model.toUi

class ChatViewModel(
    private val sendUserMessageUseCase: SendUserMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val updateSystemMessageUseCase: UpdateSystemMessageUseCase,
    private val getSystemMessageUseCase: GetSystemMessageUseCase,
    private val systemPromptProvider: SystemPromptProvider
) : BaseViewModel<State, Event, Effect>(State()) {

    init {
        observeMessagesUseCase()
            .map { messages -> messages.filter { it.role != MessageRole.SYSTEM }.map { it.toUi() } }
            .onEach { setState { copy(messages = it) } }
            .launchIn(scope)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInputChanged -> setState { copy(inputText = event.text) }
            is Event.OnSendClick -> {
                val inputText = current.inputText.trim()

                if (inputText.isEmpty() || current.isLoading) {
                    return
                }

                setState { copy(inputText = "", isLoading = true) }

                launch {
                    sendUserMessageUseCase(inputText)
                        .onFailure { error ->
                            setEffect {
                                ShowError(error.message ?: "Unknown error occurred")
                            }
                        }
                    setState { copy(isLoading = false) }
                }
            }

            is Event.OnPromptMenuClick -> {
                setState { copy(isPromptMenuExpanded = true) }
            }

            is Event.OnPromptMenuDismiss -> {
                setState { copy(isPromptMenuExpanded = false) }
            }

            is Event.OnPromptTypeSelected -> {
                setState {
                    copy(
                        isPromptMenuExpanded = false,
                        selectedPromptType = event.promptType
                    )
                }
                launch {
                    val systemPrompt = systemPromptProvider.get(event.promptType)
                    updateSystemMessageUseCase(systemPrompt)
                }
            }

            is Event.OnSystemPromptDialogOpen -> launch {
                val message = getSystemMessageUseCase()
                setState {
                    copy(
                        isSystemPromptDialogVisible = true,
                        customSystemPrompt = message?.content.orEmpty()
                    )
                }
            }

            is Event.OnSystemPromptDialogDismiss -> {
                setState { copy(isSystemPromptDialogVisible = false, customSystemPrompt = "") }
            }

            is Event.OnSystemPromptChanged -> {
                setState { copy(customSystemPrompt = event.prompt) }
            }

            is Event.OnSystemPromptApply -> {
                val prompt = current.customSystemPrompt.trim()
                if (prompt.isEmpty()) {
                    return
                }

                setState { copy(isSystemPromptDialogVisible = false) }

                launch {
                    updateSystemMessageUseCase(prompt)
                }
            }

            Event.OnSystemPromptClear -> {
                setState { copy(customSystemPrompt = "") }
            }
        }
    }
}
