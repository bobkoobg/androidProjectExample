package com.example.androidprojectexample.ui.app

sealed class RightOverlayContent {
    object Main : RightOverlayContent()
    object Profile : RightOverlayContent()
    object Unknown : RightOverlayContent()
    object None : RightOverlayContent()
}

data class AppUiState(
    val isOverlayOpen: Boolean = false,
    val rightOverlayContent: RightOverlayContent = RightOverlayContent.None
    // Add more fields here as your app grows, e.g.:
    // val selectedBottomNav: BottomNavItem = BottomNavItem.HOME
)
