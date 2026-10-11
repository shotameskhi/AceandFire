package com.example.myapp11.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.usecase.GetCharacterByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CharacterDetailUiState {
    object Loading : CharacterDetailUiState
    data class Success(val character: Character) : CharacterDetailUiState
    data class Error(val message: String) : CharacterDetailUiState
}

class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadCharacter(id: Int = 583) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailUiState.Loading
            try {
                val character = getCharacterByIdUseCase(id)
                _uiState.value = CharacterDetailUiState.Success(character)
            } catch (e: Exception) {
                _uiState.value = CharacterDetailUiState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
