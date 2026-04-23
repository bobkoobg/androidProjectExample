package com.example.androidprojectexample.ui.pager

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PagerViewModel : ViewModel() {
    private val pageStates = mutableStateMapOf<Int, PagerPageUiState>()
    private val chunkSize = 30
    private val prefetchThreshold = 4
    // Demo-only behavior: each pager tab has an unknown limit from "backend".
    private val simulatedLastChunkByPage = mapOf(0 to 5, 1 to 3, 2 to 4)

    fun pageState(page: Int): PagerPageUiState {
        return pageStates[page] ?: PagerPageUiState()
    }

    fun loadPageIfNeeded(page: Int) {
        val current = pageStates[page]

        if (current?.isLoaded == true || current?.isLoading == true || current?.isRefreshing == true) {
            return
        }

        pageStates[page] = PagerPageUiState(isLoading = true)

        viewModelScope.launch {
            // Simulate async loading. Replace with repository/network call.
            delay(1500)
            val firstChunkItems = requestItems(page = page, chunk = 1)

            pageStates[page] = PagerPageUiState(
                isLoading = false,
                isLoaded = true,
                title = when (page) {
                    0 -> "Welcome"
                    1 -> "Stats"
                    else -> "Profile teaser"
                },
                body = when (page) {
                    0 -> "This first page can show an overview or feed."
                    1 -> "This second page can show charts, metrics, or history."
                    else -> "This third page can show details or actions specific to the user."
                },
                loadCount = (current?.loadCount ?: 0) + 1,
                items = firstChunkItems,
                imageDescription = when (page) {
                    0 -> "Overview footer image"
                    1 -> "Stats footer image"
                    else -> "Profile footer image"
                },
                loadedChunks = if (firstChunkItems.isEmpty()) 0 else 1,
                hasMoreItems = firstChunkItems.isNotEmpty()
            )
        }
    }

    fun loadNextPageIfNeeded(page: Int, lastVisibleIndex: Int) {
        val current = pageStates[page] ?: return

        if (!current.isLoaded || current.isLoading || current.isRefreshing || current.isAppending || !current.hasMoreItems) {
            return
        }

        if (lastVisibleIndex < current.items.lastIndex - prefetchThreshold) {
            return
        }

        val nextChunk = current.loadedChunks + 1

        pageStates[page] = current.copy(isAppending = true)

        viewModelScope.launch {
            // Simulate async append API call.
            delay(800)

            val appendedItems = requestItems(page = page, chunk = nextChunk)

            if (appendedItems.isEmpty()) {
                // Backend says no more data.
                pageStates[page] = current.copy(
                    isAppending = false,
                    hasMoreItems = false
                )
                return@launch
            }

            pageStates[page] = current.copy(
                isAppending = false,
                isLoaded = true,
                items = current.items + appendedItems,
                loadedChunks = nextChunk,
                hasMoreItems = true
            )
        }
    }

    fun refreshPage(page: Int) {
        val current = pageStates[page]

        if (current == null || current.isLoading || current.isRefreshing) {
            return
        }

        pageStates[page] = current.copy(isRefreshing = true)

        viewModelScope.launch {
            // Simulate refresh latency before applying a new random order.
            delay(500)

            pageStates[page] = current.copy(
                isRefreshing = false,
                isLoaded = true,
                loadCount = current.loadCount + 1,
                items = current.items.ifEmpty { defaultItemsForPage(page = page, chunk = 1) }.shuffled(),
                hasMoreItems = current.hasMoreItems,
                isAppending = false
            )
        }
    }

    private fun requestItems(page: Int, chunk: Int): List<String> {
        val lastChunk = simulatedLastChunkByPage[page] ?: 2
        if (chunk > lastChunk) return emptyList()
        return defaultItemsForPage(page = page, chunk = chunk)
    }

    private fun defaultItemsForPage(page: Int, chunk: Int): List<String> {
        val start = ((chunk - 1) * chunkSize) + 1
        return List(chunkSize) { offset ->
            val index = start + offset
            when (page) {
                0 -> "Overview element $index"
                1 -> "Stats element $index"
                else -> "Profile element $index"
            }
        }
    }
}
