package com.example.tugasketujuh

import androidx.compose.ui.window.ComposeUIViewController
import com.example.tugasketujuh.database.AppDependencies
import com.example.tugasketujuh.database.IosDatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    AppDependencies.databaseDriverFactory = IosDatabaseDriverFactory()
    App()
}
