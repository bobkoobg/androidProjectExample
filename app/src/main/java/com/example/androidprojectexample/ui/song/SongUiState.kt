package com.example.androidprojectexample.ui.song

import com.example.androidprojectexample.data.model.Song

// Single source of truth - SongViewModel
//
// The SSOT is the owner of that data, and only the SSOT can modify or mutate it. To achieve this,
// the SSOT exposes the data using an immutable type; to modify the data, the SSOT exposes functions
// or receives events that other types can call.
//
// This pattern has multiple benefits:
//
//Centralizes all changes to a particular type of data in one place
//Protects the data so that other types cannot tamper with it
//Makes changes to the data more traceable, so bugs are easier to spot
//
//In an offline-first application, the source of truth for application data is typically a database.
data class SongUiState (
    val songs: List<Song> = listOf(),
    val inputText: String = ""
)