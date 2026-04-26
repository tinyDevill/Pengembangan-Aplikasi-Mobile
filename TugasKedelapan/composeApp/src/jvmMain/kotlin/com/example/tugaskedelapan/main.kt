package com.example.tugaskedelapan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.tugaskedelapan.di.initKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "TugasKetujuh",
    ) {
        App()
    }
}
