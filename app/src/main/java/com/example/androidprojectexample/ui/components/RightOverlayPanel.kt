package com.example.androidprojectexample.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true)
@Composable
fun RightOverlayPanelPreview() {
    RightOverlayPanel(
        isVisible = true,
        onClose = {}
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Preview Content")
        }
    }
}

@Composable
fun RightOverlayPanel(
    isVisible: Boolean,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    Log.d("BOYKO", "RightOverlayPanel visibility changed: $isVisible (or initialized)")

    AnimatedVisibility(
        visible = isVisible,
        enter = androidx.compose.animation.EnterTransition.None,
        exit = fadeOut(animationSpec = tween(durationMillis = 1, delayMillis = 500))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.25f))
            )
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(500),
            initialOffsetX = { fullWidth -> fullWidth }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(500),
            targetOffsetX = { fullWidth -> fullWidth }
        )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxHeight()
                    .clickable(onClick = onClose)
            )

            Box(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxHeight()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    content()
                }
            }
        }
    }
}
