package com.example.tugaskedelapan.di

import org.koin.core.context.startKoin

private object KoinBootState {
    var started: Boolean = false
}

fun initKoin() {
    if (KoinBootState.started) return
    KoinBootState.started = true
    startKoin {
        modules(appModule)
    }
}
