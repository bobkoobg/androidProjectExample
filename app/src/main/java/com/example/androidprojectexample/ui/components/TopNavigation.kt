package com.example.androidprojectexample.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(title = "Title", showBackButton = true, showMoreButton = true)
}

@Preview(showBackground = true)
@Composable
fun TopBarPreviewTwo() {
    TopBar(title = "Title", showBackButton = false, showMoreButton = true)
}

@Preview(showBackground = true)
@Composable
fun TopBarPreviewThree() {
    TopBar(title = "Title", showBackButton = false, showMoreButton = false)
}

@Preview(showBackground = true)
@Composable
fun TopBarPreviewFour() {
    TopBar(title = "Title", showBackButton = true, showMoreButton = false)
}

@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean,
    showMoreButton: Boolean,
    onBackClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(64.dp)
                .padding(horizontal = 4.dp)
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.End)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                // Keep horizontal symmetry so the title stays centered.
                Box(modifier = Modifier.width(48.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            if (showMoreButton) {
                IconButton(onClick = {
                    Log.d("BOYKO", "TopNavigation: More button clicked")
                    onMoreClick()
                }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                }
            } else {
                // Keep horizontal symmetry so the title stays centered.
                Box(modifier = Modifier.width(48.dp))
            }
        }
    }
}