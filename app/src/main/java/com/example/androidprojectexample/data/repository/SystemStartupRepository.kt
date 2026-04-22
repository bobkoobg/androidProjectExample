package com.example.androidprojectexample.data.repository

import com.example.androidprojectexample.data.remote.system.MaintenanceRemoteDataSource

class SystemStartupRepository(
    private val maintenanceRemoteDataSource: MaintenanceRemoteDataSource
) {

    suspend fun getIsMaintenance(): Boolean {
        return maintenanceRemoteDataSource.getIsMaintenance()
    }
}

