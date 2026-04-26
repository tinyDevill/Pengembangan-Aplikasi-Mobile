package com.example.tugaskedelapan.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskedelapan.device.DeviceInfo
import org.koin.compose.koinInject

@Composable
fun DeviceInfoSection(
    modifier: Modifier = Modifier,
    deviceInfo: DeviceInfo = koinInject()
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider()
        Text(
            text = "Device Info",
            style = MaterialTheme.typography.titleMedium
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Device: ${deviceInfo.getDeviceName()}")
            Text(text = "OS: ${deviceInfo.getOsVersion()}")
            Text(text = "App: ${deviceInfo.getAppVersion()}")
        }
    }
}
