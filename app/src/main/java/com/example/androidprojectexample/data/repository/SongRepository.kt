package com.example.androidprojectexample.data.repository

import android.util.Log
import com.example.androidprojectexample.data.local.SongIO
import com.example.androidprojectexample.data.model.Song
import com.example.androidprojectexample.data.remote.SongApi

class SongRepository(
    private val api: SongApi = SongApi(),
    private val dao: SongIO = SongIO()
) {
    fun getSongs(): List<Song> {
        val local = dao.getSongs()

        return local.ifEmpty {
            Log.d("BOYKO", "SongRepository: No songs found in local storage, fetching from API")
            val remote = api.fetchSongs()
            dao.saveSongs(remote)
            remote
        }
    }
}