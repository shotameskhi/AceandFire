package com.example.myapp11.presentation.houses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.House
import com.example.myapp11.domain.usecase.GetHousesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HouseViewModel(
    private val getHousesUseCase: GetHousesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var allHousesList: List<House> = emptyList()

    private val _houses = MutableStateFlow<List<House>>(emptyList())
    val houses = _houses.asStateFlow()

    init {
        loadHouses()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterHouses(query)
    }

    private fun loadHouses() {
        viewModelScope.launch {
            try {
                allHousesList = getHousesUseCase()
                filterHouses(_searchQuery.value)
            } catch (e: Exception) {
                _houses.value = emptyList()
            }
        }
    }

    private fun filterHouses(query: String) {
        if (query.isBlank()) {
            _houses.value = allHousesList
        } else {
            _houses.value = allHousesList.filter { house ->
                house.name.contains(query, ignoreCase = true) ||
                        house.region.contains(query, ignoreCase = true) ||
                        house.words.contains(query, ignoreCase = true)
            }
        }
    }
}
