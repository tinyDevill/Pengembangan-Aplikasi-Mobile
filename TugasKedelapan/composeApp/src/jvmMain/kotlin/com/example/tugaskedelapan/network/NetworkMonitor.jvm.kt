package com.example.tugaskedelapan.network

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.net.NetworkInterface

actual class NetworkMonitor {
    actual fun isConnected(): Boolean = runCatching {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val networkInterface = interfaces.nextElement()
            if (networkInterface.isUp && !networkInterface.isLoopback) {
                return@runCatching true
            }
        }
        false
    }.getOrDefault(true)

    actual fun observeConnectivity(): Flow<Boolean> = flow {
        while (true) {
            emit(isConnected())
            delay(5_000)
        }
    }.distinctUntilChanged()
}
