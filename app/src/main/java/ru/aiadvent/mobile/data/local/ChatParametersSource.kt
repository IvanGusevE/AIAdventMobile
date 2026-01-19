package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.ChatParameters

interface ChatParametersSource {
    fun observe(): Flow<ChatParameters>
    suspend fun get(): ChatParameters
    suspend fun update(parameters: ChatParameters)
    suspend fun updateSystemPrompt(prompt: String)
}
