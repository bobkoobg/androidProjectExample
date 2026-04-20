package com.example.androidprojectexample.data.remote.song

import android.util.Log
import com.example.androidprojectexample.data.model.Song
import com.example.androidprojectexample.data.remote.network.ApiProvider
import com.example.androidprojectexample.data.remote.song.api.SongApi
import com.example.androidprojectexample.data.remote.version.model.MinimumVersionResponse
import okhttp3.ResponseBody

class SongRemoteDataSource(
    private val api: SongApi = ApiProvider.api
) {

    fun fetchSongs(): List<Song> {
        Log.d("BOYKO", "fetchSongs: Fetching songs from API")
        return listOf(
            Song(1, "Jaba Jaburana"),
            Song(2, "100 mushici hvana"),
            Song(3, "v blizkata gorichka"),
            Song(420, "S edna surnichka")
        )
    }

    suspend fun getMinimumVersion(): MinimumVersionResponse {
        Log.d("BOYKO", "Calling!")
        return api.getMinimumVersion()
    }

    suspend fun addSongRaw(): ResponseBody {
        return api.addSongRaw()
    }

}