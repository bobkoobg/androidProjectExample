package com.example.androidprojectexample.ui.components.rightOverlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun RightOverlayUnknownScreenPreview() {
    RightOverlayUnknownScreen()
}


@Composable
fun RightOverlayUnknownScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "😢", style = MaterialTheme.typography.displayLarge)
    }
}

