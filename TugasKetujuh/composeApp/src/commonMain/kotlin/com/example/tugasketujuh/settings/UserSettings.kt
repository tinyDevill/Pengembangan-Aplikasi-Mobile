package com.example.tugasketujuh.settings

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class SortOrder {
    NEWEST_FIRST,
    OLDEST_FIRST,
    TITLE_ASC,
    TITLE_DESC,
    FAVORITES_FIRST
}

data class UserSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val sortOrder: SortOrder = SortOrder.NEWEST_FIRST
)
