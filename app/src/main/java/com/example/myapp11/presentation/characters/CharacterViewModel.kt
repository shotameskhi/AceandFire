package com.example.myapp11.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    init {
        loadCharacters("")
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        loadCharacters(query)
    }

    private fun loadCharacters(query: String) {
        viewModelScope.launch {
            try {
                val result = getCharactersUseCase(query.ifBlank { null })
                _characters.value = result.filter { it.name.isNotBlank() }
            } catch (e: Exception) {
                _characters.value = emptyList()
            }
        }
    }
}
