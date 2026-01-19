package ru.aiadvent.mobile.data.repository

import ru.aiadvent.mobile.data.local.ChatLocalSource
import ru.aiadvent.mobile.data.local.ChatParametersSource
import ru.aiadvent.mobile.data.remote.MistralApiService
import ru.aiadvent.mobile.data.remote.model.ChatMessageDto
import ru.aiadvent.mobile.data.remote.model.MessageRoleDto
import ru.aiadvent.mobile.data.remote.model.MistralRequest
import ru.aiadvent.mobile.data.remote.model.toRemote
import ru.aiadvent.mobile.domain.model.ChatParameters
import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole
import ru.aiadvent.mobile.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val mistralApiService: MistralApiService,
    private val localSource: ChatLocalSource,
    private val parametersSource: ChatParametersSource
) : ChatRepository {

    override fun observe() = localSource.observe()

    override suspend fun getAll(): List<Message> = localSource.getAll()

    override suspend fun refresh(messages: List<Message>) {
        localSource.refresh(messages)
    }

    override suspend fun add(message: Message) {
        localSource.add(message)

        val parameters = parametersSource.get()

        val messages = buildList {
            if (parameters.systemPrompt.isNotEmpty()) {
                add(ChatMessageDto(role = MessageRoleDto.SYSTEM, content = parameters.systemPrompt))
            }
            val history = localSource.getAll()
                .map { ChatMessageDto(role = it.role.toRemote(), content = it.content) }
            addAll(history)
        }

        val request = MistralRequest.default(
            messages = messages,
            temperature = parameters.temperature
        )

        val response = mistralApiService.send(request).getOrThrow()

        val assistantContent = response.choices.firstOrNull()?.message?.content
        if (assistantContent != null) {
            val message = Message(content = assistantContent, role = MessageRole.ASSISTANT)
            localSource.add(message)
        }
    }

    override suspend fun clear() {
        localSource.refresh(emptyList())
    }

    override suspend fun getParameters() = parametersSource.get()

    override suspend fun updateParameters(parameters: ChatParameters) {
        parametersSource.update(parameters)
    }

    override suspend fun updateSystemPrompt(prompt: String) {
        parametersSource.updateSystemPrompt(prompt)
    }
}
