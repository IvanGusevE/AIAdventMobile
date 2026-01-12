package ru.aiadvent.mobile.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.aiadvent.mobile.BuildConfig
import ru.aiadvent.mobile.data.model.MistralRequest
import ru.aiadvent.mobile.data.model.MistralResponse

interface MistralApiService {
    suspend fun sendMessage(request: MistralRequest): Result<MistralResponse>
}

class MistralApiServiceImpl(
    private val client: HttpClient
) : MistralApiService {

    companion object {
        private const val BASE_URL = "https://api.mistral.ai/v1"
        private const val CHAT_ENDPOINT = "$BASE_URL/chat/completions"
    }

    override suspend fun sendMessage(request: MistralRequest): Result<MistralResponse> {
        return runCatching {
            val response = client.post(CHAT_ENDPOINT) {
                header("Authorization", "Bearer ${BuildConfig.MISTRAL_API_KEY}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.body<MistralResponse>()
        }
    }
}
