package com.example.tugaskedelapan.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import platform.Network.NWPathMonitor
import platform.Network.NWPathStatusSatisfied
import platform.darwin.dispatch_queue_create

actual class NetworkMonitor {
    private val monitor = NWPathMonitor()

    actual fun isConnected(): Boolean = monitor.currentPath.status == NWPathStatusSatisfied

    actual fun observeConnectivity(): Flow<Boolean> = callbackFlow {
        monitor.pathUpdateHandler = { path ->
            trySend(path.status == NWPathStatusSatisfied)
        }

        trySend(isConnected())
        val queue = dispatch_queue_create("NetworkMonitorQueue", null)
        monitor.start(queue = queue)

        awaitClose {
            monitor.cancel()
        }
    }.distinctUntilChanged()
}
