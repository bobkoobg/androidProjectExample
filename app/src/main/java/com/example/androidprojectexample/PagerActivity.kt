package com.example.androidprojectexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.androidprojectexample.ui.pager.PagerScreen

class PagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BOYKO", "Starting/Reloading pager activity")
        enableEdgeToEdge()
        setContent {
            PagerScreen()
        }
    }
}