package com.example.androidprojectexample.ui.overlay

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun RightOverlayMainContentPreview() {
    RightOverlayMainContent(onClose = {})
}

@Composable
fun RightOverlayMainContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Side panel",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                Log.d("BOYKO", "RightOverlayPanel: Close button clicked (X)")
                onClose()
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close panel",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile image",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "This panel overlays your screen. Keep adding any actions and content you need here.",
            style = MaterialTheme.typography.bodyLarge
        )
        HorizontalDivider()
        repeat(25) { index ->
            Text(
                text = "Scrollable item ${index + 1}: sample content to demonstrate vertical scrolling.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

