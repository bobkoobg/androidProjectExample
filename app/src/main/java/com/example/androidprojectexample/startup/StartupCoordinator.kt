package com.example.androidprojectexample.startup

import android.util.Log
import com.example.androidprojectexample.data.repository.SystemStartupRepository
import com.example.androidprojectexample.data.repository.UserStartupRepository
import com.example.androidprojectexample.ui.startup.AppStartupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class StartupCoordinator(
    private val systemStartupRepository: SystemStartupRepository,
    private val userStartupRepository: UserStartupRepository
) {

    private val startMutex = Mutex()

    private val _startupState = MutableStateFlow<AppStartupState>(AppStartupState.Idle)
    val startupState: StateFlow<AppStartupState> = _startupState.asStateFlow()

    suspend fun startIfNeeded() {
        Log.d("BOYKO", "StartupCoordinator: startIfNeeded called")
        startMutex.withLock {
            if (
                _startupState.value is AppStartupState.Ready
                || _startupState.value is AppStartupState.Loading
            ) {
                return
            }
            _startupState.value = AppStartupState.Loading(step = "Checking maintenance")
        }

        runCatching {
            var guestUUID: String? = null
            var userId: Int? = null

            val isMaintenance = systemStartupRepository.getIsMaintenance()
            Log.d("BOYKO", "StartupCoordinator: maintenance=$isMaintenance")

            if(!isMaintenance) {
                _startupState.value = AppStartupState.Loading(step = "Getting current user")
                val fakeUuidHeader = "device-uuid-123"
                val currentUser = userStartupRepository.getCurrentUser(fakeUuidHeader)
                Log.d("BOYKO", "StartupCoordinator: currentUser=$currentUser")

                if (currentUser != null) {
                    userId = currentUser.userId
                } else {
                    _startupState.value = AppStartupState.Loading(step = "Generating guest UUID")
                    guestUUID = userStartupRepository.generateGuestUuid()
                }
            }

            val payload = StartupPayload(
                userId,
                guestUUID,
                isMaintenance
            )

            _startupState.value = AppStartupState.Ready(payload = payload)
        }.onFailure { throwable ->
            Log.e("BOYKO", "StartupCoordinator failed", throwable)
            _startupState.value = AppStartupState.Error(
                message = throwable.message ?: "Startup failed"
            )
        }
    }

    suspend fun retry() {
        startMutex.withLock {
            _startupState.value = AppStartupState.Idle
        }
        startIfNeeded()
    }
}

