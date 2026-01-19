package ru.aiadvent.mobile.domain.prompt

interface SystemPromptProvider {
    fun get(type: PromptType): String
}

enum class PromptType {
    QUESTIONNAIRE,
    JSON_STRUCTURED,
    GENERAL_ASSISTANT
}

class SystemPromptProviderImpl : SystemPromptProvider {

    override fun get(type: PromptType): String = when (type) {
        PromptType.QUESTIONNAIRE -> Questionnaire
        PromptType.JSON_STRUCTURED -> JsonStructured
        PromptType.GENERAL_ASSISTANT -> ""
    }
}
