package com.example.androidprojectexample.ui.song

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.androidprojectexample.ui.components.CentralizedImage

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.

@Composable
fun SongItem(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CentralizedImage("moderna rabota")
        Text(text = title)
    }
}