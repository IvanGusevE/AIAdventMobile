package ru.aiadvent.mobile.di

import org.koin.dsl.module
import ru.aiadvent.mobile.data.local.ChatLocalQuestionnaireSource
import ru.aiadvent.mobile.data.local.ChatLocalSource
import ru.aiadvent.mobile.data.remote.MistralApiService
import ru.aiadvent.mobile.data.repository.ChatRepositoryImpl
import ru.aiadvent.mobile.domain.repository.ChatRepository

val dataModule = module {
    single<ChatLocalSource> { ChatLocalQuestionnaireSource() }

    single { MistralApiService(client = get()) }

    single<ChatRepository> {
        ChatRepositoryImpl(
            mistralApiService = get(),
            localSource = get()
        )
    }
}
