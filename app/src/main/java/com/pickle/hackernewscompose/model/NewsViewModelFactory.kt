package com.pickle.hackernewscompose.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pickle.hackernewscompose.network.HackNewsApiHelperImpl
import com.pickle.hackernewscompose.network.NewsFeedApiCaller

/**
 * The aim of this factory is tocreate viewModel with a repo already injected at creation.
 * Here we want the Gadget Repo to be instanciated when certains viewModel class are calling this factory
 */
@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(private val repo: NewsRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsFeedViewModel::class.java) -> NewsFeedViewModel(repo, HackNewsApiHelperImpl(apiService = NewsFeedApiCaller.HNApiService)) as T
            else -> throw IllegalArgumentException("Unexpected model class $modelClass")
        } as T
    }
}