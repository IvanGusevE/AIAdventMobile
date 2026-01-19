package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.repository.ChatRepository

class GetSystemMessageUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke() = chatRepository.getAll().find { it.role == MessageRole.SYSTEM }
}
