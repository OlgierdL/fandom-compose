package org.example.project.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.data.model.Article


class ArticlesRepository {
    private val httpClient = HttpClient{
        install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
    }

    suspend fun getArticles(): List<Article> {
        val apiUrl = "https://services.fandom.com/mobile-sidekick/trending/articles"

        return httpClient.get(apiUrl).body()
    }
}