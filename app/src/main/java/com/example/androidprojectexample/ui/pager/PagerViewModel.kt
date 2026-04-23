package com.example.androidprojectexample.ui.pager

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PagerViewModel : ViewModel() {
    private val pageStates = mutableStateMapOf<Int, PagerPageUiState>()

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
                items = defaultItemsForPage(page),
                imageDescription = when (page) {
                    0 -> "Overview footer image"
                    1 -> "Stats footer image"
                    else -> "Profile footer image"
                }
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
                items = current.items.ifEmpty { defaultItemsForPage(page) }.shuffled()
            )
        }
    }

    private fun defaultItemsForPage(page: Int): List<String> {
        return List(30) { index ->
            when (page) {
                0 -> "Overview element ${index + 1}"
                1 -> "Stats element ${index + 1}"
                else -> "Profile element ${index + 1}"
            }
        }
    }
}
