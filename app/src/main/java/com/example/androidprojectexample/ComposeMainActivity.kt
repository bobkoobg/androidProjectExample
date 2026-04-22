package com.example.androidprojectexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidprojectexample.ui.app.AppUiStateViewModel
import com.example.androidprojectexample.ui.app.RightOverlayContent
import com.example.androidprojectexample.ui.components.BottomBar
import com.example.androidprojectexample.ui.components.RightOverlayPanel
import com.example.androidprojectexample.ui.components.TopBar
import com.example.androidprojectexample.ui.pager.PagerScreen
import com.example.androidprojectexample.ui.profile.ProfileScreen
import com.example.androidprojectexample.ui.song.SongScreen
import com.example.androidprojectexample.ui.overlay.RightOverlayMainContent
import com.example.androidprojectexample.ui.overlay.RightOverlayProfileContent
import com.example.androidprojectexample.ui.overlay.RightOverlayUnknownContent
import com.example.androidprojectexample.ui.startup.AppStartupState
import com.example.androidprojectexample.startup.StartupPayload
import com.example.androidprojectexample.startup.StartupViewModel

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
    val startupViewModel: StartupViewModel = viewModel()
    val startupState by startupViewModel.startupState.collectAsState()

    Log.d("BOYKO", "~~~ Current startup state: $startupState ~~~")

    // LaunchedEffect == .post { ... }
    LaunchedEffect(Unit) {
        Log.d("BOYKO", "~~~ STARTING BOOTSTRAP ~~~")
        startupViewModel.startBootstrap()
    }

    when (val state = startupState) {
        AppStartupState.Idle,
        is AppStartupState.Loading -> {
            Log.d("BOYKO", "~~~ BOOTSTRAP IS LOADING ~~~")
            val step = (state as? AppStartupState.Loading)?.step ?: "Preparing app"
            StartupLoadingScreen(step = step)
        }
        is AppStartupState.Error -> {
            StartupErrorScreen(
                message = state.message,
                onRetry = { startupViewModel.retryBootstrap() }
            )
        }
        is AppStartupState.Ready -> {
            Log.d("BOYKO", "~~~ BOOTSTRAP IS READY  state.payload ${state.payload} ~~~")
            if (state.payload.userId != null) {
                Log.d("BOYKO", "~~~ YEY, WE ARE DONE :) CASE A ~~~")
                AppMainContent()
            } else if (state.payload.guestId != null) {
                Log.d("BOYKO", "~~~ YEY, WE ARE DONE :) CASE B ~~~")
                LoggedOutRecommendationsScreen()
            } else if (state.payload.isMaintenance) {
                StartupErrorScreen(
                    message = "The app is currently under maintenance. Please try again later.",
                    onRetry = { startupViewModel.retryBootstrap() }
                )
            }
        }
    }
}

@Composable
private fun AppMainContent() {
    val navController = rememberNavController()
    val uiStateViewModel: AppUiStateViewModel = viewModel()
    val uiState by uiStateViewModel.uiState.collectAsState()
    val isOverlayOpen = uiState.isOverlayOpen
    val overlayContent = uiState.rightOverlayContent
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val topBarTitle = when (currentRoute) {
        "pager" -> "Pager"
        "profile" -> "Profile"
        else -> "Songs"
    }
    val showBackButton = currentRoute != "song"

    BackHandler(enabled = isOverlayOpen) {
        uiStateViewModel.closeOverlay()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold (
            topBar = {
                TopBar(
                    title = topBarTitle,
                    showBackButton = showBackButton,
                    showMoreButton = true,
                    onBackClick = { navController.navigateUp() },
                    onMoreClick = {
                        Log.d("BOYKO", "ComposeMainActivity: Opening overlay panel!")
                        when (currentRoute) {
                            "profile" -> uiStateViewModel.openRightOverlay(RightOverlayContent.Profile)
                            "song" -> uiStateViewModel.openRightOverlay(RightOverlayContent.Main)
                            else -> uiStateViewModel.openRightOverlay(RightOverlayContent.Unknown)
                        }
                    }
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

                composable(route = "pager") {
                    PagerScreen()
                }

                composable(route = "profile") {
                    ProfileScreen()
                }

                composable(route = "user menu") {
                    ProfileScreen()
                }
            }

        }

        RightOverlayPanel(
            isVisible = isOverlayOpen,
            onClose = {
                Log.d("BOYKO", "Closing overlay panel!")
                uiStateViewModel.closeOverlay()
            }
        ) {
            when (overlayContent) {
                is RightOverlayContent.Main -> {
                    RightOverlayMainContent(onClose = { uiStateViewModel.closeOverlay() })
                }
                is RightOverlayContent.Profile -> {
                    RightOverlayProfileContent()
                }
                is RightOverlayContent.Unknown -> {
                    RightOverlayUnknownContent()
                }
                RightOverlayContent.None -> {}
            }
        }
    }
}

@Composable
private fun LoggedOutRecommendationsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Welcome guest")
            Text(text = "Hello Motto!")
        }
    }
}

@Composable
private fun StartupLoadingScreen(step: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator()
            Text(text = "Loading app data...")
            Text(text = step)
        }
    }
}

@Composable
private fun StartupErrorScreen(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Startup failed")
            Text(text = message)
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

