package com.example.androidprojectexample.data.repository

import com.example.androidprojectexample.data.model.user.CurrentUser
import com.example.androidprojectexample.data.remote.user.CurrentUserRemoteDataSource
import com.example.androidprojectexample.data.remote.user.GuestUuidRemoteDataSource

class UserStartupRepository(
    private val currentUserRemoteDataSource: CurrentUserRemoteDataSource,
    private val guestUuidRemoteDataSource: GuestUuidRemoteDataSource
) {

    suspend fun getCurrentUser(uuidHeader: String): CurrentUser? {
        return currentUserRemoteDataSource.getCurrentUser(uuidHeader)
    }

    suspend fun generateGuestUuid(): String {
        return guestUuidRemoteDataSource.generateGuestUuid()
    }
}

