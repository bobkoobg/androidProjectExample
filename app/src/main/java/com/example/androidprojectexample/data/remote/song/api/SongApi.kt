package com.example.androidprojectexample.data.remote.song.api

import com.example.androidprojectexample.data.remote.version.model.MinimumVersionResponse
import okhttp3.ResponseBody
import retrofit2.http.GET

interface SongApi {

    @GET("api/v3_0_1/system/minimum_application_version")
    suspend fun getMinimumVersion(): MinimumVersionResponse

    @GET("api/v3_0_1/system/minimum_application_version")
    suspend fun addSongRaw(): ResponseBody

}