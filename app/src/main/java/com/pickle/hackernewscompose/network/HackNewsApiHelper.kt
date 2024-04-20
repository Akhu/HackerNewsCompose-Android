package com.pickle.hackernewscompose.network

import HNItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HackNewsApiHelper {

    suspend fun getItemDetails(itemId: String) : Response<HNItem>

    fun getTopStories() : Flow<List<Int>>
}