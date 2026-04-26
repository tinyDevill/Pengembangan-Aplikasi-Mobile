package com.example.tugasketujuh

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.tugasketujuh.database.AppDependencies
import com.example.tugasketujuh.database.DesktopDatabaseDriverFactory

fun main() = application {
    AppDependencies.databaseDriverFactory = DesktopDatabaseDriverFactory()

    Window(
        onCloseRequest = ::exitApplication,
        title = "TugasKelima",
    ) {
        App()
    }
}
