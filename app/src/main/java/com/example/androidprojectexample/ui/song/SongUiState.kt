package com.example.androidprojectexample.ui.song

import com.example.androidprojectexample.data.model.Song

// Single source of truth
data class SongUiState (
    val songs: ArrayList<Song> = arrayListOf(),
    val inputText: String = ""
)