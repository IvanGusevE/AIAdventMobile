package ru.aiadvent.mobile.presentation.chat

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.aiadvent.mobile.core.base.BaseViewModel
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendMessageUseCase
import ru.aiadvent.mobile.presentation.chat.model.toUi

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase
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
                    sendMessageUseCase(inputText)
                        .onFailure { error ->
                            setEffect {
                                Effect.ShowError(error.message ?: "Unknown error occurred")
                            }
                        }
                    setState { copy(isLoading = false) }
                }
            }
        }
    }
}
