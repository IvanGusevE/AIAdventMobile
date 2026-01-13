package ru.aiadvent.mobile.data.local

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.aiadvent.mobile.domain.model.Message
import ru.aiadvent.mobile.domain.model.MessageRole

class ChatLocalJsonSource : ChatLocalSource {

    private val messages = MutableStateFlow(
        listOf(
            Message(
                role = MessageRole.SYSTEM,
                content = SYSTEM_MESSAGE,
                timestamp = System.currentTimeMillis()
            )
        )
    )

    override fun observe(): Flow<List<Message>> = messages

    override suspend fun getAll(): List<Message> = messages.value

    override suspend fun add(message: Message) {
        if (message.role == MessageRole.ASSISTANT) {
            val format = Json.decodeFromString<AssistantMessageFormat>(message.content)
            Log.d("ChatLocalJsonSource", format.toString())
        }
        messages.update { it + message }
    }

    @Serializable
    private data class AssistantMessageFormat(
        val tags: List<String>,
        val title: String,
        val answer: String,
        val mood: String
    )
}

private val SYSTEM_MESSAGE = """
You are a language model operating as a machine-to-machine component.

YOUR OUTPUT IS CONSUMED BY A PROGRAM, NOT A HUMAN.

You MUST return RAW JSON ONLY.
DO NOT use markdown.
DO NOT wrap the response in ``` or ```json.
DO NOT add formatting, comments, explanations, or any extra text.

The output must start with '{' and end with '}'.
Any characters before or after the JSON object are STRICTLY FORBIDDEN.

The response MUST strictly conform to the following schema and be valid JSON:

{
  "tags": string[],
  "title": string,
  "answer": string,
  "mood": string
}

STRICT RULES:
1. Always return ALL fields exactly as defined.
2. Do not add, remove, rename, or reorder fields.
3. Do not return null values.
4. Do not return an array as the root object.
5. Do not include markdown, code blocks, or backticks under any circumstances.
6. The JSON must be directly deserializable without preprocessing.

Field constraints:
- "tags": array of 1–10 semantic strings related to the topic.
- "title": concise topic title, max 120 characters.
- "answer": complete and self-contained response to the user query.
- "mood": single word or short phrase describing tone (e.g. "professional", "analytical").

If the user request is invalid or cannot be fulfilled:
- still return a JSON object with the exact same schema
- explain the issue only inside the "answer" field
- NEVER break the structure.

REMEMBER:
RAW JSON ONLY.
NO MARKDOWN.
NO ```json.
NO EXTRA TEXT.
""".trimIndent()