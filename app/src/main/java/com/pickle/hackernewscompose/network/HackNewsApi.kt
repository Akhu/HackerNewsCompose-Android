package com.pickle.hackernewscompose.network

import HNItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

data class JsonError(val error: String)

data class JsonSuccess(val success: Boolean = true)

class APIService {
    companion object {
        val client = OkHttpClient()
        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

interface NewsService {
    @GET("/v0/topstories.json")
    suspend fun topStories(): List<Int>

    @GET("/v0/item/{id}.json")
    suspend fun getItemDetails(@Path("id") itemId: String): Response<HNItem>
}

/**
 * Main entry point for network access.
 */
object NewsFeedApiCaller {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("${Config.PROTOCOL}${Config.HOST}")
        .addConverterFactory(MoshiConverterFactory.create(APIService.moshi))
        .build()

    val HNApiService: NewsService = retrofit.create(NewsService::class.java)
}

object Config {
    var PROTOCOL = "https://"
    var HOST = "hacker-news.firebaseio.com"
    var API_VERSION = "v0"
    var PORT = 8082
}
