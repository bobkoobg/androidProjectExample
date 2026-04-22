package com.example.androidprojectexample.ui.startup

import com.example.androidprojectexample.startup.StartupPayload


sealed interface AppStartupState {

    object Idle : AppStartupState

    data class Loading(val step: String) : AppStartupState

    data class Ready(val payload: StartupPayload) : AppStartupState

    data class Error(val message: String) : AppStartupState

}