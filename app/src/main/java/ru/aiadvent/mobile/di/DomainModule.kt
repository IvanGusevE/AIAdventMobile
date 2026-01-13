package ru.aiadvent.mobile.di

import org.koin.dsl.module
import ru.aiadvent.mobile.domain.usecase.ObserveMessagesUseCase
import ru.aiadvent.mobile.domain.usecase.SendMessageUseCase

val domainModule = module {
    factory { SendMessageUseCase(chatRepository = get()) }
    factory { ObserveMessagesUseCase(chatRepository = get()) }
}
