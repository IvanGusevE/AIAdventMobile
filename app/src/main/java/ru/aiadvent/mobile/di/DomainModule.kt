package ru.aiadvent.mobile.di

import org.koin.dsl.module
import ru.aiadvent.mobile.domain.prompt.SystemPromptProvider
import ru.aiadvent.mobile.domain.prompt.SystemPromptProviderImpl
import ru.aiadvent.mobile.domain.usecase.GetSystemMessageUseCase
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendUserMessageUseCase
import ru.aiadvent.mobile.domain.usecase.UpdateSystemMessageUseCase

val domainModule = module {
    single<SystemPromptProvider> { SystemPromptProviderImpl() }
    factory { ObserveMessagesUseCase(get()) }
    factory { SendUserMessageUseCase(get()) }
    factory { UpdateSystemMessageUseCase(get()) }
    factory { GetSystemMessageUseCase(get()) }
}
