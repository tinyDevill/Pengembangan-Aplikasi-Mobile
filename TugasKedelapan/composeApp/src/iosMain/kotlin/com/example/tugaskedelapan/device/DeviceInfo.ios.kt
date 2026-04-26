package com.example.tugaskedelapan.device

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

actual class DeviceInfo {
    actual fun getDeviceName(): String = UIDevice.currentDevice.name

    actual fun getOsVersion(): String = "iOS ${UIDevice.currentDevice.systemVersion}"

    actual fun getAppVersion(): String {
        val bundle = NSBundle.mainBundle
        val version = bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "1.0.0"
        val build = bundle.objectForInfoDictionaryKey("CFBundleVersion") as? String ?: "1"
        return "$version ($build)"
    }
}
