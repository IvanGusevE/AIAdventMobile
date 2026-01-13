package ru.aiadvent.mobile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MistralRequest(
    val model: String,
    val messages: List<ChatMessageDto>,
    @SerialName("max_tokens")
    val maxTokens: Int? = null,
    val temperature: Double
) {

    companion object {
        fun default(
            messages: List<ChatMessageDto>,
            model: String = "mistral-large-latest",
            temperature: Double = 0.7
        ) = MistralRequest(
            model = model,
            messages = messages,
            temperature = temperature
        )
    }
}
