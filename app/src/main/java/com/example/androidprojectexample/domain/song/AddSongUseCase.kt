package com.example.androidprojectexample.domain.song

import android.util.Log
import com.example.androidprojectexample.data.repository.SongRepository

class AddSongUseCase(
    private val repository: SongRepository
) {

    suspend fun execute(title: String, isLoggedIn: Boolean): AddSongResult {
        Log.d("BOYKO", "execute called with title: $title, isLoggedIn: $isLoggedIn")
        if (!isLoggedIn) return AddSongResult.NotLoggedIn
        if (title.length <= 3) return AddSongResult.TooShort
        if (!title.contains("potato", ignoreCase = true)) return AddSongResult.MissingKeyword

        val response = repository.addSongRaw("pesho")

        Log.d("BOYKO", "execute addSong response: $response")

        return AddSongResult.Success
    }
}