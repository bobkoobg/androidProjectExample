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

    private var songList: List<Song> = listOf(
        Song(1, "Jaba Jaburana"),
        Song(2, "100 mushici hvana"),
        Song(3, "v blizkata gorichka"),
        Song(4, "S edna surnichka"),
        Song(5, "trugnal kos"),
        Song(6, "s dulug nos"),
        Song(7, "jultokliuno patence"),
        Song(8, "ela nasam"),
        Song(9, "racho kapitna"),
        Song(10, "s parahod")
    )

    fun fetchSongs(): List<Song> {
        Log.d("BOYKO", "fetchSongs: Fetching songs from API")
        return songList
    }

    suspend fun getMinimumVersion(): MinimumVersionResponse {
        Log.d("BOYKO", "Calling!")
        return api.getMinimumVersion()
    }

    suspend fun addSongRaw(title: String): ResponseBody {
        songList = songList + Song(songList.size + 1, title)
        return api.addSongRaw()
    }

}