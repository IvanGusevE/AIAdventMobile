package ru.aiadvent.mobile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.aiadvent.mobile.presentation.chat.ChatViewModel

val viewModelModule = module {
    viewModel {
        ChatViewModel(
            sendUserMessageUseCase = get(),
            observeMessagesUseCase = get(),
            observeChatInteractionsUseCase = get(),
            getChatParametersUseCase = get(),
            updateChatParametersUseCase = get(),
            clearChartUseCase = get(),
            systemPromptProvider = get()
        )
    }
}
