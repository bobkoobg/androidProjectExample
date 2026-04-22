package com.example.androidprojectexample.ui.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppUiStateViewModel : ViewModel() {
    // backing property pattern
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    fun openOverlay() {
        _uiState.update { it.copy(isOverlayOpen = true) }
    }

    fun openRightOverlay(content: RightOverlayContent) {
        _uiState.update { it.copy(isOverlayOpen = true, rightOverlayContent = content) }
    }

    fun closeOverlay() {
        _uiState.update { it.copy(isOverlayOpen = false, rightOverlayContent = RightOverlayContent.None) }
    }
}
