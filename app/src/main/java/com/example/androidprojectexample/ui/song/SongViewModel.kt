package com.example.androidprojectexample.ui.song

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.androidprojectexample.data.model.Song
import com.example.androidprojectexample.data.repository.SongRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// State holders (such as ViewModel) that hold data, expose it to the UI, and handle logic.
// State holders should live for the same duration as the UI element they are providing state for.
// For example, a ViewModel for a screen should be retained in memory until the screen is removed
// from the app's navigation back stack.\
//
// SSOT for UI state
class SongViewModel(
    private val repository: SongRepository = SongRepository()
) : ViewModel() {

    // ViewModel = what it means

    var uiState by mutableStateOf(SongUiState())
        private set // Anyone can read uiState. Only this class can change it

    init {
        loadSongs()
    }

    fun onSongAddInputChange(text: String) {
        uiState = uiState.copy(
            inputText = text
        )
    }

    fun addSong() {
        val text = uiState.inputText
        if (text.isBlank()) return

        val newSong = Song(
            id = uiState.songs.size + 1,
            title = text
        )

        uiState = uiState.copy(
            songs = (uiState.songs + newSong),
            inputText = "" // clear input
        )
    }

    private fun loadSongs() {
        Log.d("BOYKO", "SongViewModel: Loading songs from repository")
        uiState = uiState.copy(
            songs = repository.getSongs()
        )
    }
}