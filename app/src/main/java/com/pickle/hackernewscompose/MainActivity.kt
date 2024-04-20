package com.pickle.hackernewscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.pickle.hackernewscompose.model.NewsFeedViewModel
import com.pickle.hackernewscompose.model.NewsViewModelFactory
import com.pickle.hackernewscompose.ui.theme.HackerNewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsViewModel: NewsFeedViewModel by viewModels {
            NewsViewModelFactory((this.application as HackerNewsComposeApp).newsRepository)
        }
        setContent {
            HackerNewsComposeTheme {
            // A surface container using the 'background' color from the theme
                HackerNewsApp(newsFeedViewModel = newsViewModel)
            }
        }
    }
}

@Composable
fun HackerNewsApp(newsFeedViewModel: NewsFeedViewModel) {
    //val navController = rememberNavController()
    NewsFeedScreen(newsFeedViewModel = newsFeedViewModel)
}