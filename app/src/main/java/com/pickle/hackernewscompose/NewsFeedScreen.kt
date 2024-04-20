@file:OptIn(ExperimentalMaterial3Api::class)

package com.pickle.hackernewscompose

import HNItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInBrowser
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pickle.hackernewscompose.model.NewsFeedViewModel
import com.pickle.hackernewscompose.model.UiState
import dateTimeToString
import sampleItem

@Composable
fun NewsFeedScreen(modifier: Modifier = Modifier,
                   newsFeedViewModel: NewsFeedViewModel) {
    val handler = LocalUriHandler.current
    val composeUiState = newsFeedViewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Center) {
        NewsFeedList(
            modifier = modifier,
            composeUiState = composeUiState.value,
            onRefreshClicked = { newsFeedViewModel.loadTopStories() },
            handler = handler
        )
    }

}

@Composable
fun NewsFeedList(
    modifier: Modifier = Modifier,
    composeUiState: UiState,
    onRefreshClicked: () -> Unit,
    handler: UriHandler) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text("Hacker News Feed") },
                navigationIcon = {
                    IconButton(onClick = { onRefreshClicked() }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Refresh content")
                    }
                })
        },
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .fillMaxSize()) {
            when(composeUiState) {
                is UiState.StartUp -> {
                    Text("Start up")
                }
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
                }
                is UiState.LoadedSuccess -> {
                    val data = composeUiState.items
                    LazyColumn {
                        items(items = data, key = { item -> item.id}){newsItem ->
                           NewsFeedItem(newsItem = newsItem, handler = handler)
                        }
                    }
                }
                is UiState.Error -> {
                    Text("Error")
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewsFeedItem(newsItem: HNItem, handler: UriHandler) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(newsItem.type.capitalize(Locale.current) ?: "Unknown",
                        Modifier.padding(4.dp))
                }
                Text(newsItem.title ?: "Untitled", style = MaterialTheme.typography.headlineSmall)
                Row {
                    val text = buildAnnotatedString {
                        withStyle(MaterialTheme.typography.bodySmall.toSpanStyle()) {
                            append(newsItem.dateTimeToString() ?: "")
                            append(" by ")
                            append(newsItem.by ?: "Unknown")
                        }
                    }
                    Text(text = text)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    newsItem.url?.let {
                        FilledTonalButton(
                            onClick = {
                                handler.openUri(it)
                            }) {
                            Text("Open URL")
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(Icons.Rounded.OpenInBrowser, contentDescription = "")
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 600,
    widthDp = 300
)
@Composable
fun NewsFeedScreenPreview() {
    val handler = LocalUriHandler.current

    val composeUiState by remember {
        mutableStateOf(
            UiState.LoadedSuccess(
            items = mutableListOf(
                sampleItem,
                sampleItem.copy(
                    id = 293,
                    title = "My 20 year career is technical debt or deprecated"),
                sampleItem.copy(
                    id= 493,
                    title = "Artists must be allowed to make bad work"),
                sampleItem.copy(
                    id= 490,
                    title = "Artists must be allowed to make bad work")
            )
        ))
    }
    NewsFeedList(
        composeUiState = composeUiState,
        onRefreshClicked = { /*TODO*/ },
        handler = handler
    )

}