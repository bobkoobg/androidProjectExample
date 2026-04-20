package com.example.androidprojectexample.data.remote.version.model

data class MinimumVersionResponse(
    val android: AndroidVersion,
    val ios: IosVersion
)