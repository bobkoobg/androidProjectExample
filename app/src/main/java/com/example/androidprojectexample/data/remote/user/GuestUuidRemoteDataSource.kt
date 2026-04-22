package com.example.androidprojectexample.data.remote.user

import com.example.androidprojectexample.data.remote.song.api.SongApi
import java.util.UUID
import kotlinx.coroutines.delay

class GuestUuidRemoteDataSource(
    private val api: SongApi
) {

    suspend fun generateGuestUuid(): String {
        delay(1000)
        return UUID.randomUUID().toString()
    }
}

