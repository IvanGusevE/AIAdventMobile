package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.repository.ChatRepository

class SendUserMessageUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(content: String) = runCatching {
        val userMessage = Message(
            content = content.trim(),
            role = MessageRole.USER,
            timestamp = System.currentTimeMillis()
        )
        chatRepository.add(userMessage)
    }
}
