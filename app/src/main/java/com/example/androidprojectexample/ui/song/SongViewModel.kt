package com.example.androidprojectexample.ui.song

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.androidprojectexample.data.model.Song
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojectexample.BazaarApplication
import com.example.androidprojectexample.domain.song.AddSongResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    // SongViewModel does _events.tryEmit(SongUiEvent.ShowSnackbar("..."))
    // MutableSharedFlow<SongUiEvent> - a hot stream that can emit many SongUiEvent values (like ShowSnackbar), and multiple collectors can observe it.
    // private val _events - only the ViewModel can emit into it (tryEmit/emit). UI cannot push events back in.
    // extraBufferCapacity = 1 - keeps one event in buffer if collector is momentarily busy/not ready. Without buffer, tryEmit can fail more easily if nothing is ready to collect right now.
    // val events = _events.asSharedFlow() - exposes a read-only view. SongScreen can collect events, but cannot emit or mutate the flow.

    private val _events = MutableSharedFlow<SongUiEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

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
                        _events.tryEmit(SongUiEvent.ShowSnackbar("Song \"$text\" added"))
                    }

                    AddSongResult.NotLoggedIn -> {
                        Log.d("BOYKO", "SongViewModel : addSong failed, user not logged in")
                        _events.tryEmit(SongUiEvent.ShowSnackbar("You must be logged in to add a song."))
                    }

                    AddSongResult.TooShort -> {
                        Log.d("BOYKO", "SongViewModel : addSong failed, title too short")
                        _events.tryEmit(SongUiEvent.ShowSnackbar("Title is too short (min 4 characters)."))
                    }

                    AddSongResult.MissingKeyword -> {
                        Log.d("BOYKO", "SongViewModel : addSong failed, title missing keyword")
                        _events.tryEmit(SongUiEvent.ShowSnackbar("Title must contain the word \"potato\"."))
                    }
                }

            } catch (e: Exception) {
                // handle error
                Log.d("BOYKO", "Batkooo e $e")
                _events.tryEmit(SongUiEvent.ShowSnackbar("Something went wrong: ${e.message ?: "unknown error"}"))
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