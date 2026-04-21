package com.example.androidprojectexample.ui.song

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.

@Preview(showBackground = true)
@Composable
private fun SongScreenPreview() {
    SongScreen(
        onNavigateToPager = {}
    )
}

@Composable
fun SongScreen(
    onNavigateToPager: () -> Unit
) {

    val songViewModel: SongViewModel = viewModel()

    // collectAsState bridges Flow → Compose state
    val state by songViewModel.uiState.collectAsState()

    // with remember → “keep it while this screen lives” ✔
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect - starts a coroutine; cancels it when Composable leaves screen; restarts if key changes
    // SongScreen collects events in LaunchedEffect and calls snackbarHostState.showSnackbar(...) - message is shown once, then gone
    LaunchedEffect(Unit) {
        // collectLatest - cancels previous work if new event comes
        songViewModel.events.collectLatest { event ->
            when (event) {
                is SongUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Log.d("BOYKO", "SongScreen: Received ${state.songs.size} songs from ViewModel")

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row {
                BasicTextField(
                    value = state.inputText,
                    onValueChange = {
                        Log.d("BOYKO", "Pisha neshto novo :)")
                        songViewModel.onSongAddInputChange(it)
                    },
                    modifier = Modifier
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                )
                Button(
                    onClick =  {
                        Log.d("BOYKO", "Adding song: ${state.inputText}")
                        songViewModel.addSong()
                    }
                ) {
                    Text("Add song")
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            LazyColumn {
                items(state.songs) { song ->
                    SongItem(title = song.title)
                }
            }
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Button(
                onClick =  {
                    Log.d("BOYKO", "THIS IS A CLICK EVENT - Meaning that we are going to the pager activity")
                    onNavigateToPager()
                }
            ) {
                Text("Go to PagerActivity")
            }
        }

    }
}