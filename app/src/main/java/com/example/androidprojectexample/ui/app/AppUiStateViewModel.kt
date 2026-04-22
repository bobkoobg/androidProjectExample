package com.example.androidprojectexample.ui.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppUiStateViewModel : ViewModel() {
    // backing property pattern
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    fun openRightOverlay(content: RightOverlayContent) {
        _uiState.update { it.copy(isRightOverlayOpen = true, rightOverlayContent = content) }
    }

    fun closeRightOverlay() {
        _uiState.update { it.copy(isRightOverlayOpen = false) }
    }

    fun openUserMenu() {
        _uiState.update { it.copy(isUserMenuOpen = true) }
    }

    fun closeUserMenu() {
        _uiState.update { it.copy(isUserMenuOpen = false) }
    }
}
