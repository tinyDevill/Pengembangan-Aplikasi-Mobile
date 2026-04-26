package com.example.tugaskedelapan

import androidx.compose.ui.window.ComposeUIViewController
import com.example.tugaskedelapan.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
