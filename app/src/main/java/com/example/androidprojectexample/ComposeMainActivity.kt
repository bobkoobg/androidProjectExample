package com.example.androidprojectexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidprojectexample.ui.components.BottomBar
import com.example.androidprojectexample.ui.components.TopBar
import com.example.androidprojectexample.ui.pager.PagerScreen
import com.example.androidprojectexample.ui.profile.ProfileScreen
import com.example.androidprojectexample.ui.song.SongScreen

class ComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BOYKO", "Starting/Reloading main activity")

        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val topBarTitle = when (currentRoute) {
        "pager" -> "Pager"
        "profile" -> "Profile"
        else -> "Songs"
    }
    val showBackButton = currentRoute != "song"

    Scaffold (
        topBar = {
            TopBar(
                title = topBarTitle,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "song",
            modifier = Modifier.padding(padding)
        ) {

            composable(route = "song") {
                SongScreen()
            }

            composable("pager") {
                PagerScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        }

    }

}
