package com.example.androidprojectexample.ui.song

sealed interface SongDialogModel {
    // Approve + Decline
    data class Delete(val songName: String) : SongDialogModel

    // Only one button based on condition: true = Approve only, false = Decline only
    data class Add(val songName: String, val showApprove: Boolean) : SongDialogModel

    // Approve + Decline + Try Again
    data class Update(val songName: String) : SongDialogModel
}