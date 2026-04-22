package com.example.androidprojectexample.ui.song

import com.example.androidprojectexample.data.model.Song
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.

@Composable
fun SongScreen() {
    Log.d("BOYKO", "Composable SongScreen loaded!")
    val songViewModel: SongViewModel = viewModel()

    LaunchedEffect(Unit) {
        Log.d("BOYKO", "LaunchedEffect triggered only once!")
        songViewModel.loadSongs()
    }

    // collectAsState bridges Flow → Compose state
    val state by songViewModel.uiState.collectAsState()

    // with remember → “keep it while this screen lives” ✔
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // LaunchedEffect - starts a coroutine; cancels it when Composable leaves screen; restarts if key changes
    // SongScreen collects events in LaunchedEffect and calls snackbarHostState.showSnackbar(...) - message is shown once, then gone
    LaunchedEffect(Unit) {
        Log.d("BOYKO", "events triggered hehe!")

        // collectLatest - cancels previous work if new event comes
        songViewModel.events.collectLatest { event ->
            when (event) {
                is SongUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    SongScreenContent(
        state = state,
        onSongAddInputChange = songViewModel::onSongAddInputChange,
        onAddSong = {
            Log.d("BOYKO", "Adding song: ${state.inputText}")
            focusManager.clearFocus(force = true)
            keyboardController?.hide()
            songViewModel.addSong()
        },
        onDoSomethingClick = {
            Log.d("BOYKO", "THIS IS A CLICK EVENT - Meaning that we want something to happen")
            focusManager.clearFocus(force = true)
            keyboardController?.hide()
            songViewModel.onDoSomethingClick()
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun SongScreenContent(
    state: SongUiState,
    onSongAddInputChange: (String) -> Unit,
    onAddSong: () -> Unit,
    onDoSomethingClick: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Row {
                    BasicTextField(
                        value = state.inputText,
                        onValueChange = onSongAddInputChange,
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    )
                    Button(onClick = onAddSong) {
                        Text("Add song")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(state.songs) { song ->
                SongItem(title = song.title)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(onClick = onDoSomethingClick) {
                    Text("Do something")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SongScreenPreview() {
    SongScreenContent(
        state = SongUiState(
            songs = listOf(
                Song(id = 1, title = "Numb"),
                Song(id = 2, title = "In the End"),
                Song(id = 3, title = "Breaking the Habit")
            ),
            inputText = "Papercut"
        ),
        onSongAddInputChange = {},
        onAddSong = {},
        onDoSomethingClick = {}
    )
}