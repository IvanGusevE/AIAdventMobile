package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import ru.aiadvent.mobile.domain.model.ChatInteraction

interface ChatInteractionSource {
    fun observe(): Flow<List<ChatInteraction>>
    fun add(chatInteraction: ChatInteraction)
    fun clear()
}
