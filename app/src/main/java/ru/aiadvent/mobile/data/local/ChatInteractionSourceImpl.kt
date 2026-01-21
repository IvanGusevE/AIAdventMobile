package ru.aiadvent.mobile.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.aiadvent.mobile.domain.model.ChatInteraction

class ChatInteractionSourceImpl : ChatInteractionSource {

    private val interactions = MutableStateFlow(emptyList<ChatInteraction>())

    override fun observe(): Flow<List<ChatInteraction>> = interactions

    override fun add(chatInteraction: ChatInteraction) {
        interactions.update { it + chatInteraction }
    }

    override fun clear() {
        interactions.update { emptyList() }
    }
}
