package com.example.androidprojectexample.di

import com.example.androidprojectexample.data.local.SongLocalDataSource
import com.example.androidprojectexample.data.remote.song.SongRemoteDataSource
import com.example.androidprojectexample.data.remote.song.api.SongApi
import com.example.androidprojectexample.data.repository.SongRepository
import com.example.androidprojectexample.domain.song.AddSongUseCase

class SongContainer(
    private val api: SongApi
)  {

    // data layer
    private val songRemoteDataSource by lazy {
        SongRemoteDataSource(api)
    }

    private val songLocalDataSource by lazy {
        SongLocalDataSource()
    }

    val songRepository by lazy {
        SongRepository(songRemoteDataSource, songLocalDataSource)
    }

    // domain layer
    val addSongUseCase by lazy {
        AddSongUseCase(songRepository)
    }

}