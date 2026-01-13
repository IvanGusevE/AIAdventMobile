package ru.aiadvent.mobile.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.repository.ChatRepository

class ObserveMessagesUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<List<Message>> = chatRepository.observe()
}
