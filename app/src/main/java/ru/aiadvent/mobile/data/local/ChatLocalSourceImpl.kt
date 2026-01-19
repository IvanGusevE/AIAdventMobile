package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.aiadvent.mobile.domain.model.Message

class ChatLocalSourceImpl : ChatLocalSource {

    private val messages = MutableStateFlow(emptyList<Message>())

    override fun observe(): Flow<List<Message>> = messages

    override suspend fun getAll(): List<Message> = messages.value

    override suspend fun add(message: Message) {
        messages.update { it + message }
    }

    override suspend fun refresh(messages: List<Message>) {
        this.messages.update { messages }
    }
}
