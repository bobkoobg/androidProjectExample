package com.example.androidprojectexample.ui.song

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidprojectexample.ui.components.CentralizedImage

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.

@Composable
fun SongItem(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CentralizedImage(
            description = "moderna rabota",
            modifier = Modifier.size(10.dp)
        )
        Text(text = title)
    }
}