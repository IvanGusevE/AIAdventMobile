package ru.aiadvent.mobile.domain.usecase

import ru.aiadvent.mobile.domain.repository.ChatRepository

class ClearChartUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke() = chatRepository.clear()
}
