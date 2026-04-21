package com.example.androidprojectexample.ui.pager

data class PagerPageUiState(
    val isLoading: Boolean = false,
    val isLoaded: Boolean = false,
    val title: String = "",
    val body: String = "",
    val loadCount: Int = 0,
    val items: List<String> = emptyList(),
    val imageDescription: String = ""
)