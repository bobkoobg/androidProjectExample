package com.example.androidprojectexample.domain.song

sealed class AddSongResult {
    object Success : AddSongResult()
    object NotLoggedIn : AddSongResult()
    object TooShort : AddSongResult()
    object MissingKeyword : AddSongResult()
}