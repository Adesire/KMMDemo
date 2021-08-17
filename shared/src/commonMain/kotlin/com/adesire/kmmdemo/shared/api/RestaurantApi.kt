package com.adesire.kmmdemo.shared.api

import com.adesire.kmmdemo.shared.BuildKonfig
import com.adesire.kmmdemo.shared.Restaurant
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class RestaurantApi {
    private val httpClient = HttpClient {
        install(Logging){
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(JsonFeature) {
            val json: Json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getRestaurantList(): List<Restaurant> {
        return httpClient.get(BuildKonfig.BASE_URL_API.plus("?size=20"))
    }

}