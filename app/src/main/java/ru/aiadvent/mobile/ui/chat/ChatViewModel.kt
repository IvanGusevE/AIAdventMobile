package ru.aiadvent.mobile.ui.chat

import android.util.Log
import ru.aiadvent.mobile.base.BaseViewModel
import ru.aiadvent.mobile.data.model.ChatMessage
import ru.aiadvent.mobile.data.model.MessageRole
import ru.aiadvent.mobile.data.model.MistralRequest
import ru.aiadvent.mobile.data.remote.MistralApiService
import java.util.UUID

class ChatViewModel(
    private val mistralApiService: MistralApiService
) : BaseViewModel<ChatState, ChatEvent, ChatEffect>(ChatState()) {

    companion object {
        private const val TAG = "ChatViewModel"
    }

    override fun handleEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnInputChanged -> handleInputChanged(event.text)
            is ChatEvent.OnSendClick -> handleSendMessage()
        }
    }

    private fun handleInputChanged(text: String) {
        setState { copy(inputText = text) }
    }

    private fun handleSendMessage() {
        val inputText = current.inputText.trim()

        if (inputText.isEmpty() || current.isLoading) {
            return
        }

        // Add user message immediately
        val userMessage = ChatUiMessage(
            id = UUID.randomUUID().toString(),
            content = inputText,
            role = MessageRole.USER
        )

        setState {
            copy(
                messages = messages + userMessage,
                inputText = "",
                isLoading = true,
            )
        }

        // Send request to Mistral API
        launch {
            val conversationHistory = buildConversationHistory()
            val request = MistralRequest(
                model = "mistral-large-latest",
                messages = conversationHistory,
                temperature = 0.7,
            )

            mistralApiService.sendMessage(request)
                .onSuccess { response ->
                    val assistantContent = response.choices.firstOrNull()?.message?.content
                    if (assistantContent != null) {
                        val assistantMessage = ChatUiMessage(
                            id = UUID.randomUUID().toString(),
                            content = assistantContent,
                            role = MessageRole.ASSISTANT
                        )
                        setState {
                            copy(
                                messages = messages + assistantMessage,
                                isLoading = false
                            )
                        }
                    } else {
                        handleApiError("Empty response from API")
                    }
                }
                .onFailure { error ->
                    Log.e(TAG, "API error", error)
                    handleApiError(error.message ?: "Unknown error occurred")
                }
        }
    }

    private fun buildConversationHistory(): List<ChatMessage> = current.messages.map { uiMessage ->
        ChatMessage(
            role = uiMessage.role,
            content = uiMessage.content
        )
    }

    private fun handleApiError(message: String) {
        setState { copy(isLoading = false) }
        setEffect { ChatEffect.ShowError(message) }
    }
}
