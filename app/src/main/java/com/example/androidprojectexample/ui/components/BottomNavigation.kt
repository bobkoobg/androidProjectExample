package com.example.androidprojectexample.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar(rememberNavController())
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("song", "pager", "profile", "user menu")
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    NavigationBar {
        items.forEach { route ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute == route) return@NavigationBarItem

                    Log.d("BOYKO", "Navigating to $route")
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (route) {
                            "song" -> Icons.Default.MusicNote
                            "pager" -> Icons.Default.Pages
                            "user menu" -> Icons.Default.AllInclusive
                            else -> Icons.Default.Person
                        },
                        contentDescription = route
                    )
                },
                label = { Text(route) }
            )
        }
    }
}