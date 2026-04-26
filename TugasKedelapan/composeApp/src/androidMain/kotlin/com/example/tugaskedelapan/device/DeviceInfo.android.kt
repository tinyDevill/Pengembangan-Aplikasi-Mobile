package com.example.tugaskedelapan.device

import android.os.Build
import com.example.tugaskedelapan.database.AndroidPlatformContextHolder

actual class DeviceInfo {
    actual fun getDeviceName(): String = Build.MODEL ?: "Android Device"

    actual fun getOsVersion(): String = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

    actual fun getAppVersion(): String {
        val context = AndroidPlatformContextHolder.context
        @Suppress("DEPRECATION")
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName ?: "1.0.0"
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toString()
        }
        return "$versionName ($versionCode)"
    }
}
