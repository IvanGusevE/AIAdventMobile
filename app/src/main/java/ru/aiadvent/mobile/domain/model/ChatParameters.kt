package ru.aiadvent.mobile.domain.model

data class ChatParameters(
    val temperature: Double = DEFAULT_TEMPERATURE,
    val systemPrompt: String = DEFAULT_SYSTEM_PROMPT,
    val model: MistralModel = DEFAULT_MODEL
) {
    companion object {
        const val DEFAULT_TEMPERATURE = 0.7
        const val DEFAULT_SYSTEM_PROMPT = "You are a helpful AI assistant."
        val DEFAULT_MODEL = MistralModel.DEFAULT

        const val MIN_TEMPERATURE = 0.0
        const val MAX_TEMPERATURE = 2.0
    }
}
