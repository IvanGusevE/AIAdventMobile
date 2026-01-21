package ru.aiadvent.mobile.di

import org.koin.dsl.module
import ru.aiadvent.mobile.data.local.ChatInteractionSource
import ru.aiadvent.mobile.data.local.ChatInteractionSourceImpl
import ru.aiadvent.mobile.data.local.ChatLocalSourceImpl
import ru.aiadvent.mobile.data.local.ChatLocalSource
import ru.aiadvent.mobile.data.local.ChatParametersSource
import ru.aiadvent.mobile.data.local.ChatParametersSourceImpl
import ru.aiadvent.mobile.data.remote.MistralApiService
import ru.aiadvent.mobile.data.repository.ChatRepositoryImpl
import ru.aiadvent.mobile.domain.repository.ChatRepository

val dataModule = module {
    single<ChatLocalSource> { ChatLocalSourceImpl() }

    single<ChatParametersSource> { ChatParametersSourceImpl() }

    single<ChatInteractionSource> { ChatInteractionSourceImpl() }

    single { MistralApiService(client = get()) }

    single<ChatRepository> {
        ChatRepositoryImpl(
            mistralApiService = get(),
            localSource = get(),
            parametersSource = get(),
            interactionSource = get()
        )
    }
}
