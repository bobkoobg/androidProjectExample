package com.example.androidprojectexample.data.remote

import android.util.Log
import com.example.androidprojectexample.data.model.Song

class SongApi {

    fun fetchSongs(): ArrayList<Song> {
        Log.d("BOYKO", "fetchSongs: Fetching songs from API")
        return arrayListOf(
            Song(1, "Jaba Jaburana"),
            Song(2, "100 mushici hvana"),
            Song(3, "v blizkata gorichka"),
            Song(420, "S edna surnichka")
        )
    }

}