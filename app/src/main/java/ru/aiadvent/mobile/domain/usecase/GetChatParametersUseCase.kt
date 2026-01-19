package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.model.ChatParameters
import ru.aiadvent.mobile.domain.repository.ChatRepository

class GetChatParametersUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): ChatParameters = chatRepository.getParameters()
}
