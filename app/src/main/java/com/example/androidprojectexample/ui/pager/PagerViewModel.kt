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
                items = List(30) { index ->
                    when (page) {
                        0 -> "Overview element ${index + 1}"
                        1 -> "Stats element ${index + 1}"
                        else -> "Profile element ${index + 1}"
                    }
                },
                imageDescription = when (page) {
                    0 -> "Overview footer image"
                    1 -> "Stats footer image"
                    else -> "Profile footer image"
                }
            )
        }
    }
}
