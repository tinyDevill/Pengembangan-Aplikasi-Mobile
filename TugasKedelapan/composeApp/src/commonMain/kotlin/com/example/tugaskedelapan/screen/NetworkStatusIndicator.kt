package com.example.tugaskedelapan.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskedelapan.network.NetworkMonitor
import org.koin.compose.koinInject

@Composable
fun NetworkStatusIndicator(
    modifier: Modifier = Modifier,
    networkMonitor: NetworkMonitor = koinInject()
) {
    val connectivityFlow = remember(networkMonitor) { networkMonitor.observeConnectivity() }
    val isConnected by connectivityFlow.collectAsState(initial = networkMonitor.isConnected())

    AnimatedVisibility(visible = !isConnected) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.CloudOff, contentDescription = "Offline")
                Text(text = "No Internet Connection")
            }
        }
    }
}
