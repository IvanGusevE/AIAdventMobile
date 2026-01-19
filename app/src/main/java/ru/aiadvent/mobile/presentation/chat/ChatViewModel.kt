package ru.aiadvent.mobile.presentation.chat

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.aiadvent.mobile.core.base.BaseViewModel
import ru.aiadvent.mobile.domain.model.ChatParameters
import ru.aiadvent.mobile.domain.prompt.SystemPromptProvider
import ru.aiadvent.mobile.domain.usecase.ClearChartUseCase
import ru.aiadvent.mobile.domain.usecase.GetChatParametersUseCase
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendUserMessageUseCase
import ru.aiadvent.mobile.domain.usecase.UpdateChatParametersUseCase
import ru.aiadvent.mobile.presentation.chat.Effect.ShowError
import ru.aiadvent.mobile.presentation.chat.model.toUi

class ChatViewModel(
    private val sendUserMessageUseCase: SendUserMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val getChatParametersUseCase: GetChatParametersUseCase,
    private val updateChatParametersUseCase: UpdateChatParametersUseCase,
    private val systemPromptProvider: SystemPromptProvider,
    private val clearChartUseCase: ClearChartUseCase,
) : BaseViewModel<State, Event, Effect>(State()) {

    init {
        observeMessagesUseCase()
            .map { messages -> messages.map { it.toUi() } }
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
                    updateChatParametersUseCase.updateSystemPrompt(systemPrompt)
                }
            }

            is Event.OnChatParamsDialogOpen -> launch {
                val parameters = getChatParametersUseCase()
                val paramsDialog = ChatParamsDialog(
                    systemPrompt = parameters.systemPrompt,
                    temperature = parameters.temperature
                )
                setState { copy(paramsDialog = paramsDialog) }
            }

            is Event.OnChatParamsDialogDismiss -> {
                setState { copy(paramsDialog = null) }
            }

            is Event.OnChatParamsDialogApply -> {
                setState { copy(paramsDialog = null) }

                val params = ChatParameters(
                    systemPrompt = event.systemPrompt,
                    temperature = event.temperature
                )

                launch { updateChatParametersUseCase(params) }
            }

            Event.OnClearChatClick -> {
                launch { clearChartUseCase() }
            }
        }
    }
}
