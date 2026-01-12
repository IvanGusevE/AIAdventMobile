package ru.aiadvent.mobile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.aiadvent.mobile.ui.chat.ChatViewModel

val viewModelModule = module {
    viewModel {
        ChatViewModel(
            mistralApiService = get()
        )
    }
}
