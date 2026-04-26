package com.example.tugaskedelapan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tugaskedelapan.database.AndroidPlatformContextHolder
import com.example.tugaskedelapan.di.initKoin
import com.example.tugaskedelapan.settings.AndroidSettingsContextHolder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        AndroidPlatformContextHolder.context = applicationContext
        AndroidSettingsContextHolder.filesDir = filesDir
        initKoin()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
