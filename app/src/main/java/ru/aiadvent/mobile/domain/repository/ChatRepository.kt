package ru.aiadvent.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.ChatParameters
import ru.aiadvent.mobile.domain.model.Message

interface ChatRepository {
    fun observe(): Flow<List<Message>>
    suspend fun getAll(): List<Message>
    suspend fun refresh(messages: List<Message>)
    suspend fun add(message: Message)
    suspend fun clear()
    
    suspend fun getParameters(): ChatParameters
    suspend fun updateParameters(parameters: ChatParameters)
    suspend fun updateSystemPrompt(prompt: String)
}
