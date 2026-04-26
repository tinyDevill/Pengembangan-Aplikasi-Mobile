package com.example.tugasketujuh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tugasketujuh.database.AndroidDatabaseDriverFactory
import com.example.tugasketujuh.database.AppDependencies
import com.example.tugasketujuh.settings.AndroidSettingsContextHolder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        AppDependencies.databaseDriverFactory = AndroidDatabaseDriverFactory(this)
        AndroidSettingsContextHolder.filesDir = filesDir
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
