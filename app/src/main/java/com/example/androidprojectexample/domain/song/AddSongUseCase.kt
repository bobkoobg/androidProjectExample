package com.example.androidprojectexample.domain.song

import android.util.Log
import com.example.androidprojectexample.data.repository.SongRepository

// The domain layer is an optional layer between the UI and data layers.
//
// The domain layer is responsible for encapsulating complex business logic or simpler business logic
// that is reused by multiple view models. The domain layer is optional because not all apps have these
// requirements. Use it only when needed - for example, to handle complexity or favor reusability.
//
// When it is included, the optional domain layer provides dependencies to the UI layer and depends
// on the data layer.
//
// Classes in the domain layer are commonly called use cases or interactors. Each use case has responsibility
// for a single functionality. For example, your app could have a GetTimeZoneUseCase class if multiple
// view models rely on time zones to display the proper message on the screen.
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