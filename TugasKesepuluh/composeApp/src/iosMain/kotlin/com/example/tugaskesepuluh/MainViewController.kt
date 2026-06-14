package com.example.tugaskesepuluh

import androidx.compose.ui.window.ComposeUIViewController
import com.example.tugaskesepuluh.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
