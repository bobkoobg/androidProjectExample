package com.example.androidprojectexample.ui.song

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import com.example.androidprojectexample.data.model.Song
import com.example.androidprojectexample.data.repository.SongRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojectexample.BazaarApplication
import com.example.androidprojectexample.domain.song.AddSongResult
import com.example.androidprojectexample.domain.song.AddSongUseCase
import kotlinx.coroutines.launch

// State holders (such as ViewModel) that hold data, expose it to the UI, and handle logic.
// State holders should live for the same duration as the UI element they are providing state for.
// For example, a ViewModel for a screen should be retained in memory until the screen is removed
// from the app's navigation back stack.\
//
// SSOT for UI state
class SongViewModel(application: Application) : AndroidViewModel(application) {

    // ViewModel = what it means

    private val container = (application as BazaarApplication).songContainer
    private val addSongUseCase = container.addSongUseCase
    private val repository = container.songRepository

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

        val isLoggedIn = true // hardcoded for now, but later we will get it from somewhere else

        Log.d("BOYKO", "SongViewModel : addSong")
        viewModelScope.launch {
            try {

                when (addSongUseCase.execute(text, isLoggedIn)) {

                    AddSongResult.Success -> {

                        Log.d("BOYKO", "SongViewModel : addSong success, adding song to UI state")

                        val newSong = Song(
                            id = uiState.songs.size + 1,
                            title = text
                        )

                        uiState = uiState.copy(
                            songs = uiState.songs + newSong,
                            inputText = ""
                        )

                    }

                    AddSongResult.NotLoggedIn -> {

                        Log.d("BOYKO", "SongViewModel : addSong failed, user not logged in")
                        // show message later
                    }

                    AddSongResult.TooShort -> {

                        Log.d("BOYKO", "SongViewModel : addSong failed, title too short")
                        // show message
                    }

                    AddSongResult.MissingKeyword -> {
                        Log.d("BOYKO", "SongViewModel : addSong failed, title missing keyword")
                        // show message
                    }
                }

            } catch (e: Exception) {
                // handle error
                Log.d("BOYKO", "Batkooo e $e")
            }
        }


    }

    private fun loadSongs() {
        Log.d("BOYKO", "SongViewModel: Loading songs from repository")
        uiState = uiState.copy(
            songs = repository.getSongs()
        )
    }
}