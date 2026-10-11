package com.example.myapp11.presentation.houses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.House
import com.example.myapp11.domain.usecase.GetHouseByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HouseDetailUiState {
    object Loading : HouseDetailUiState
    data class Success(val house: House) : HouseDetailUiState
    data class Error(val message: String) : HouseDetailUiState
}

class HouseDetailViewModel(
    private val getHouseByIdUseCase: GetHouseByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HouseDetailUiState>(HouseDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadHouse(id: Int = 378) {
        viewModelScope.launch {
            _uiState.value = HouseDetailUiState.Loading
            try {
                val house = getHouseByIdUseCase(id)
                _uiState.value = HouseDetailUiState.Success(house)
            } catch (e: Exception) {
                _uiState.value = HouseDetailUiState.Error(e.localizedMessage ?: "Error loading house")
            }
        }
    }
}
