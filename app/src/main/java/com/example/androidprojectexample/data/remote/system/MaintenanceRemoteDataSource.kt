package com.example.androidprojectexample.data.remote.system

import com.example.androidprojectexample.data.remote.song.api.SongApi
import kotlinx.coroutines.delay

class MaintenanceRemoteDataSource(
    private val api: SongApi
) {

    suspend fun getIsMaintenance(): Boolean {
        delay(2500)
        return false
    }
}

