package com.example.hiringlistdemo.Services

import com.example.hiringlistdemo.Models.Item
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get


import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun queryAllItems(): List<Item> {
        val response = client.get("https://fetch-hiring.s3.amazonaws.com/hiring.json")
        return response.body();
    }
}
