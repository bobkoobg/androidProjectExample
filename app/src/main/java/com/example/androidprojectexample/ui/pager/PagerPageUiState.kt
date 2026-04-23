package com.example.androidprojectexample.ui.pager

data class PagerPageUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isAppending: Boolean = false,
    val isLoaded: Boolean = false,
    val title: String = "",
    val body: String = "",
    val loadCount: Int = 0,
    val items: List<String> = emptyList(),
    val imageDescription: String = "",
    val loadedChunks: Int = 0,
    val hasMoreItems: Boolean = true,
    val deletingItems: Set<String> = emptySet()
)