package com.example.androidprojectexample

import android.app.Application
import com.example.androidprojectexample.data.remote.network.ApiProvider
import com.example.androidprojectexample.di.CarContainer
import com.example.androidprojectexample.di.SongContainer

class BazaarApplication : Application() {

    private val api by lazy {
        ApiProvider.api
    }

    val songContainer by lazy { SongContainer(api) }

    val carContainer by lazy { CarContainer() }

}