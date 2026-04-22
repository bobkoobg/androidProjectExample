package com.example.androidprojectexample.data.remote.user

import com.example.androidprojectexample.data.model.user.CurrentUser
import com.example.androidprojectexample.data.remote.song.api.SongApi
import kotlinx.coroutines.delay

class CurrentUserRemoteDataSource(
    private val api: SongApi
) {

    private val shouldReturnUserId = true

    suspend fun getCurrentUser(uuidHeader: String): CurrentUser? {
        delay(1500)
        val isValidHeader = uuidHeader.isNotBlank()

        return if (shouldReturnUserId && isValidHeader) {
            CurrentUser(userId = 123)
        } else {
            null
        }
    }
}

