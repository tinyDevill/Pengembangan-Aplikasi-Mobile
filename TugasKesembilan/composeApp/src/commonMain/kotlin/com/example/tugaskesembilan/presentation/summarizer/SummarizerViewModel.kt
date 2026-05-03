package com.example.tugaskesembilan.presentation.summarizer

import androidx.lifecycle.ViewModel
import com.example.tugaskesembilan.data.ai.AIRepository
import com.example.tugaskesembilan.data.model.SummarizerUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SummarizerViewModel(
    private val repository: AIRepository
) : ViewModel() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _uiState = MutableStateFlow(SummarizerUiState())
    val uiState: StateFlow<SummarizerUiState> = _uiState.asStateFlow()

    fun onInputChange(value: String) {
        _uiState.update { it.copy(inputText = value, error = null) }
    }

    fun clearAll() {
        _uiState.value = SummarizerUiState()
    }

    fun summarize() {
        val input = uiState.value.inputText.trim()
        if (input.isBlank()) {
            _uiState.update { it.copy(error = "Teks tidak boleh kosong.") }
            return
        }

        _uiState.update {
            it.copy(isLoading = true, error = null, resultText = "")
        }

        scope.launch {
            repository.summarize(input)
                .onSuccess { summary ->
                    _uiState.update {
                        it.copy(isLoading = false, resultText = summary)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "Terjadi kesalahan saat memproses AI."
                        )
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
