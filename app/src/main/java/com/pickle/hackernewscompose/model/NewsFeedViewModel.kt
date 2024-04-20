package com.pickle.hackernewscompose.model

import HNItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pickle.hackernewscompose.network.HackNewsApiHelperImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class UiState() {
    object StartUp: UiState()
    object Loading: UiState()
    data class LoadedSuccess(val items: List<HNItem>) : UiState()
    object Error: UiState()
}

class NewsFeedViewModel(val repository: NewsRepository, private val apiHelper: HackNewsApiHelperImpl) : ViewModel() {

    var uiState = MutableStateFlow<UiState>(UiState.StartUp)
        private set


    init {
        loadTopStories()
    }

    fun loadTopStories() {
        viewModelScope.launch(Dispatchers.Main) {
            uiState.value = UiState.Loading
            val topStoriesAsItems = mutableListOf<HNItem>()

            apiHelper.getTopStories()
                .flatMapConcat { ids ->
                    flow {
                        ids.take(10).forEach {
                            emit(it)
                        }
                    }
                }
                .mapNotNull {
                    val response = apiHelper.getItemDetails(it.toString())
                    if (response.isSuccessful) {
                        response.body()?.let { item ->
                            return@mapNotNull item
                        } ?: run {
                            return@mapNotNull null
                        }
                    } else {
                        null
                    }
                }
                .flowOn(Dispatchers.IO)
                .catch {
                    Timber.d(it.toString())
                    uiState.value = UiState.Error
                }
                .onCompletion {
                    Timber.d("Completed")
                    uiState.value = UiState.LoadedSuccess(topStoriesAsItems)
                }
                .collect {
                    topStoriesAsItems.add(it)
                    Timber.d(it.title)
                }
        }
    }

}