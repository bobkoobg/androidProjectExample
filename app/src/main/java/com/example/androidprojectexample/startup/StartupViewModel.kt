package com.example.androidprojectexample.startup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojectexample.BazaarApplication
import com.example.androidprojectexample.ui.startup.AppStartupState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartupViewModel(application: Application) : AndroidViewModel(application) {

    private val startupCoordinator = (application as BazaarApplication).startupCoordinator

    val startupState: StateFlow<AppStartupState> = startupCoordinator.startupState

    fun startBootstrap() {
        viewModelScope.launch {
            startupCoordinator.startIfNeeded()
        }
    }

    fun retryBootstrap() {
        viewModelScope.launch {
            startupCoordinator.retry()
        }
    }
}

