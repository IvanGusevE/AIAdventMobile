package ru.aiadvent.mobile.domain.model

data class Message(
    val content: String,
    val role: MessageRole,
    val timestamp: Long = System.currentTimeMillis()
)
