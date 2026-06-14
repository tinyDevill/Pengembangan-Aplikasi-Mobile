package com.example.tugaskesepuluh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tugaskesepuluh.database.AndroidPlatformContextHolder
import com.example.tugaskesepuluh.di.initKoin
import com.example.tugaskesepuluh.settings.AndroidSettingsContextHolder

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
