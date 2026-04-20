package com.example.androidprojectexample.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// UI elements that render the data on the screen.
// You build these elements using Jetpack Compose functions to support adaptive layouts.
@Preview(showBackground = true)
@Composable
private fun PagerScreenPreview() {
    PagerScreen(onNavigateToPager = {})
}


@Composable
fun PagerScreen(
    onNavigateToPager: () -> Unit
) {

    // UI = how it moves
    val pagerState = rememberPagerState(pageCount = { 3 })

    Scaffold { innerPadding ->
        Column {
            Text(
                text = "Pager screen Hello!",
                modifier = Modifier
                    .padding(innerPadding)
            )
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            HorizontalPager (
                state = pagerState,
                modifier = Modifier.fillMaxSize().background(Color.Red)
            ) {
                page ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Page $page",
                            color = Color.White
                        )
                    }
            }
        }
    }

}