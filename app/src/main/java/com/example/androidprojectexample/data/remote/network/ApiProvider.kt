package com.example.androidprojectexample.data.remote.network

import com.example.androidprojectexample.data.remote.song.api.SongApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://bazar.bg/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: SongApi = retrofit.create(SongApi::class.java)

}
