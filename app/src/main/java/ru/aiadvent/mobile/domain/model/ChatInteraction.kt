package ru.aiadvent.mobile.domain.model

data class ChatInteraction(
    val completionTokens: Int,
    val promptTokens: Int,
    val totalTokens: Int,
    val created: Long,
    val model: String,
    val responseTimeMs: Long
)
