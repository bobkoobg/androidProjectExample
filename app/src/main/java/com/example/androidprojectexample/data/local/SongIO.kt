package com.example.androidprojectexample.data.local

import com.example.androidprojectexample.data.model.Song

class SongIO {

    private var songs = emptyList<Song>()

    fun saveSongs(newSongs: List<Song>) {
        songs = newSongs.toList()
    }

    fun getSongs(): List<Song> {
        return songs
    }

}