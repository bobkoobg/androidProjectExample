package com.example.androidprojectexample.data.local

import com.example.androidprojectexample.data.model.Song

class SongIO {

    private val songs = ArrayList<Song>()

    fun saveSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
    }

    fun getSongs(): ArrayList<Song> {
        return songs
    }

}