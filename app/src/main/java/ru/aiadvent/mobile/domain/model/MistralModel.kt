package ru.aiadvent.mobile.domain.model

enum class MistralModel(val modelId: String, val displayName: String) {
    MISTRAL_LARGE_3("mistral-large-2512", "Mistral Large"),
    MISTRAL_MEDIUM_3_1("mistral-medium-2508", "Mistral Medium"),
    MISTRAL_SMALL_3_2("mistral-small-2506", "Mistral Small");

    companion object {
        val DEFAULT = MISTRAL_LARGE_3
    }
}
