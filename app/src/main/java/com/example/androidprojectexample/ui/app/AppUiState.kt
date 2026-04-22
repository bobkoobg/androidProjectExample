package com.example.androidprojectexample.ui.app

sealed class RightOverlayContent {
    object Main : RightOverlayContent()
    object Profile : RightOverlayContent()
    object Unknown : RightOverlayContent()
}

data class AppUiState(
    val isRightOverlayOpen: Boolean = false,
    val rightOverlayContent: RightOverlayContent? = null,
    val isUserMenuOpen: Boolean = false
)
