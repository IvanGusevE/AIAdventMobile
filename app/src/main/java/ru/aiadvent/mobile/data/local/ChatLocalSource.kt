package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.Message

interface ChatLocalSource {
    fun observe(): Flow<List<Message>>

    suspend fun getAll(): List<Message>

    suspend fun add(message: Message)
}
