package com.example.androidprojectexample.ui.pager

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidprojectexample.ui.components.CentralizedImage
import com.example.androidprojectexample.ui.components.SpinningImage

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.
@Preview(showBackground = true)
@Composable
private fun PagerScreenPreview() {
    PagerPageContent(
        page = 0,
        state = PagerPageUiState(
            isLoaded = true,
            title = "Preview page",
            body = "This is a preview of one horizontally swipeable page.",
            loadCount = 1,
            items = List(5) { "Preview element ${it + 1}" },
            imageDescription = "Preview footer image"
        ),
        onRefresh = {},
        onNearEndReached = {}
    )
}

@Composable
fun PagerScreen() {
    val pagerViewModel: PagerViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = { 3 })

    // Load the newly visible page once; ViewModel caches loaded data.
//    LaunchedEffect(pagerState.currentPage) {
//        Log.d("BOYKO", "Loaded newly visible page ${pagerState.currentPage}")
//        pagerViewModel.loadPageIfNeeded(pagerState.currentPage)
//    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val pageState = pagerViewModel.pageState(page)

        // If Compose precomposes nearby pages, this still loads each page only once.
        LaunchedEffect(page) {
            Log.d("BOYKO", "Precompose nearby page $page")
            pagerViewModel.loadPageIfNeeded(page)
        }

        PagerPageContent(
            page = page,
            state = pageState,
            onRefresh = { pagerViewModel.refreshPage(page) },
            onNearEndReached = { lastVisibleIndex ->
                pagerViewModel.loadNextPageIfNeeded(page = page, lastVisibleIndex = lastVisibleIndex)
            }
        )
    }
}

@Composable
private fun PagerPageContent(
    page: Int,
    state: PagerPageUiState,
    onRefresh: () -> Unit,
    onNearEndReached: (lastVisibleIndex: Int) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (page) {
                    0 -> Color(0xFF263238)
                    1 -> Color(0xFF1B5E20)
                    else -> Color(0xFF0D47A1)
                }
            )
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            SpinningImage(
                description = "Loading page ${page + 1}",
                modifier = Modifier.size(72.dp)
            )
        } else {
            PullToRefreshBox(
                state = pullToRefreshState,
                isRefreshing = state.isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = state.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                    }

                    item {
                        Text(
                            text = state.body,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }

                    item {
                        Text(
                            text = "Loaded ${state.loadCount} time(s)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    itemsIndexed(state.items, key =
                        { index, item -> "$page-$index-$item" }
                    ) { index, item ->
                        Log.d("BOYKO", "Hello, I am walking through elements index $index, item $item")
                        if (
                            state.hasMoreItems
                            && !state.isAppending
                            && index >= state.items.lastIndex - 1
                        ) {
                            LaunchedEffect(state.items.size) {
                                Log.d("BOYKO", "I've reached the end dudah , index $index")
                                onNearEndReached(index)
                            }
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                state.isAppending -> {
                                    Log.d("BOYKO", "I am querying new items!")
                                    CircularProgressIndicator(modifier = Modifier.size(22.dp))
                                }
                                !state.hasMoreItems -> {
                                    Log.d("BOYKO", "That's all folks :)")
                                    Text(
                                        text = "No more items",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CentralizedImage(
                                description = state.imageDescription,
                                modifier = Modifier.size(140.dp)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }

}