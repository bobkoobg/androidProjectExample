package com.example.androidprojectexample

import android.app.Application
import com.example.androidprojectexample.data.remote.network.ApiProvider
import com.example.androidprojectexample.data.remote.system.MaintenanceRemoteDataSource
import com.example.androidprojectexample.data.remote.user.CurrentUserRemoteDataSource
import com.example.androidprojectexample.data.remote.user.GuestUuidRemoteDataSource
import com.example.androidprojectexample.data.repository.SystemStartupRepository
import com.example.androidprojectexample.data.repository.UserStartupRepository
import com.example.androidprojectexample.di.CarContainer
import com.example.androidprojectexample.di.SongContainer
import com.example.androidprojectexample.startup.StartupCoordinator

class BazaarApplication : Application() {

    private val api by lazy {
        ApiProvider.api
    }

    private val maintenanceRemote by lazy {
        MaintenanceRemoteDataSource(api)
    }

    private val currentUserRemote by lazy {
        CurrentUserRemoteDataSource(api)
    }

    private val guestUuidRemote by lazy {
        GuestUuidRemoteDataSource(api)
    }

    private val systemStartupRepository by lazy {
        SystemStartupRepository(maintenanceRemote)
    }

    private val userStartupRepository by lazy {
        UserStartupRepository(currentUserRemote, guestUuidRemote)
    }

    val songContainer by lazy { SongContainer(api) }

    val startupCoordinator by lazy {
        StartupCoordinator(
            systemStartupRepository = systemStartupRepository,
            userStartupRepository = userStartupRepository
        )
    }

    val carContainer by lazy { CarContainer() }

}