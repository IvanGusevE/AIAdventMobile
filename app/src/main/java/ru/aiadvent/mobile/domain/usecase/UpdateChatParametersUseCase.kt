package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.model.ChatParameters
import ru.aiadvent.mobile.domain.repository.ChatRepository

class UpdateChatParametersUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(parameters: ChatParameters): Result<Unit> = runCatching {
        require(parameters.temperature in ChatParameters.MIN_TEMPERATURE..ChatParameters.MAX_TEMPERATURE) {
            "Temperature must be between ${ChatParameters.MIN_TEMPERATURE} and ${ChatParameters.MAX_TEMPERATURE}"
        }
        chatRepository.updateParameters(parameters)
    }

    suspend fun updateSystemPrompt(prompt: String) {
        chatRepository.updateSystemPrompt(prompt)
    }
}
