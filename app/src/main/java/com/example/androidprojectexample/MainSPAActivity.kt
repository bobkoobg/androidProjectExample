package com.example.androidprojectexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidprojectexample.ui.pager.PagerScreen
import com.example.androidprojectexample.ui.song.SongScreen

class MainSPAActivity : ComponentActivity() {

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

    NavHost(
        navController = navController,
        startDestination = "song"
    ) {

        composable(route = "song") {
            SongScreen(
                onNavigateToPager = {
                    navController.navigate("pager")
                }
            )
        }

        composable("pager") {
            PagerScreen(
                onNavigateToPager = {
                    navController.navigate("pager")
                }
            )
        }
    }
}

