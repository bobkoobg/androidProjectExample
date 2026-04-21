package com.example.androidprojectexample.ui.song

sealed interface SongUiEvent {
    data class ShowSnackbar(val message: String) : SongUiEvent
}

