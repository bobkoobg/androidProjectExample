package com.example.androidprojectexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidprojectexample.ui.song.SongScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BOYKO", "Starting/Reloading main activity")
        enableEdgeToEdge()
        setContent {
            SongScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSongList() {
    SongScreen()
}

