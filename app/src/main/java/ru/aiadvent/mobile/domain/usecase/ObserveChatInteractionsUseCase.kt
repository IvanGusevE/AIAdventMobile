package ru.aiadvent.mobile.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.ChatInteraction
import ru.aiadvent.mobile.domain.repository.ChatRepository

class ObserveChatInteractionsUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<List<ChatInteraction>> = chatRepository.observeInteractions()
}
