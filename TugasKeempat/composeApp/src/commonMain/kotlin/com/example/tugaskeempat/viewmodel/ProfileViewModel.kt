package com.example.tugaskeempat.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.tugaskeempat.data.ProfileUiState

class ProfileViewModel {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun updateProfile(name: String, bio: String) {
        _uiState.update {
            it.copy(
                name = name,
                bio = bio
            )
        }
    }

    fun toggleDarkMode() {
        _uiState.update {
            it.copy(isDarkMode = !it.isDarkMode)
        }
    }
}