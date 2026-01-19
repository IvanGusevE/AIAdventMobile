package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.aiadvent.mobile.domain.model.ChatParameters

class ChatParametersSourceImpl : ChatParametersSource {

    private val parametersFlow = MutableStateFlow(ChatParameters())

    override fun observe(): Flow<ChatParameters> = parametersFlow.asStateFlow()

    override suspend fun get(): ChatParameters = parametersFlow.value

    override suspend fun update(parameters: ChatParameters) {
        parametersFlow.value = parameters
    }

    override suspend fun updateSystemPrompt(prompt: String) {
        parametersFlow.value = parametersFlow.value.copy(systemPrompt = prompt)
    }
}
