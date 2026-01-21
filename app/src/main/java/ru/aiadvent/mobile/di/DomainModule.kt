package ru.aiadvent.mobile.di

import org.koin.dsl.module
import ru.aiadvent.mobile.domain.prompt.SystemPromptProvider
import ru.aiadvent.mobile.domain.prompt.SystemPromptProviderImpl
import ru.aiadvent.mobile.domain.usecase.ClearChartUseCase
import ru.aiadvent.mobile.domain.usecase.GetChatParametersUseCase
import ru.aiadvent.mobile.domain.usecase.ObserveChatInteractionsUseCase
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendUserMessageUseCase
import ru.aiadvent.mobile.domain.usecase.UpdateChatParametersUseCase

val domainModule = module {
    single<SystemPromptProvider> { SystemPromptProviderImpl() }
    factory { ObserveMessagesUseCase(get()) }
    factory { ObserveChatInteractionsUseCase(get()) }
    factory { SendUserMessageUseCase(get()) }
    factory { GetChatParametersUseCase(get()) }
    factory { UpdateChatParametersUseCase(get()) }
    factory { ClearChartUseCase(get()) }
}
