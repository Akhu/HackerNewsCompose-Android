package com.pickle.hackernewscompose

import android.app.Application
import com.pickle.hackernewscompose.model.NewsRepository

class HackerNewsComposeApp : Application() {
    val newsRepository by lazy { NewsRepository() }
}