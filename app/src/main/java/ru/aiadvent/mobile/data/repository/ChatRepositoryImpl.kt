package ru.aiadvent.mobile.data.repository

import ru.aiadvent.mobile.data.local.ChatLocalSource
import ru.aiadvent.mobile.data.remote.MistralApiService
import ru.aiadvent.mobile.data.remote.model.ChatMessageDto
import ru.aiadvent.mobile.data.remote.model.MistralRequest
import ru.aiadvent.mobile.data.remote.model.toRemote
import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val mistralApiService: MistralApiService,
    private val localSource: ChatLocalSource
) : ChatRepository {

    override fun observe() = localSource.observe()

    override suspend fun add(message: Message) {
        localSource.add(message)

        val history = localSource.getAll()
            .map { ChatMessageDto(role = it.role.toRemote(), content = it.content) }

        val request = MistralRequest.default(messages = history)

        val response = mistralApiService.send(request).getOrThrow()

        val assistantContent = response.choices.firstOrNull()?.message?.content
        if (assistantContent != null) {
            val message = Message(content = assistantContent, role = MessageRole.ASSISTANT)
            localSource.add(message)
        }
    }
}
