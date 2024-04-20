package com.pickle.hackernewscompose.network

import HNItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HackNewsApiHelperImpl(private val apiService: NewsService) : HackNewsApiHelper {
    override suspend fun getItemDetails(itemId: String) = apiService.getItemDetails(itemId = itemId)

    override fun getTopStories(): Flow<List<Int>> = flow { emit(apiService.topStories()) }

}