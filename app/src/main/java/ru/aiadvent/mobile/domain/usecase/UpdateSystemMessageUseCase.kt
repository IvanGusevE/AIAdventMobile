package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.repository.ChatRepository

class UpdateSystemMessageUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(content: String) {
        val systemMessage = Message(
            content = content.trim(),
            role = MessageRole.SYSTEM,
            timestamp = System.currentTimeMillis()
        )
        val messages = chatRepository.getAll()
        val updated = if (messages.firstOrNull()?.role == MessageRole.SYSTEM) {
            messages.toMutableList().apply { this[0] = systemMessage }
        } else {
            listOf(systemMessage) + messages
        }
        chatRepository.refresh(updated)
    }
}
