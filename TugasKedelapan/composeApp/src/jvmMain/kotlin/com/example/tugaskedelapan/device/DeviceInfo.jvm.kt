package com.example.tugaskedelapan.device

actual class DeviceInfo {
    actual fun getDeviceName(): String = System.getProperty("os.name") ?: "Desktop"

    actual fun getOsVersion(): String = System.getProperty("os.version") ?: "Unknown"

    actual fun getAppVersion(): String = System.getProperty("app.version") ?: "1.0.0"
}
