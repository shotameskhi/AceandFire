package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.House
import com.example.myapp11.domain.repository.HouseRepository

class GetHousesUseCase(
    private val repository: HouseRepository
) {
    suspend operator fun invoke(name: String? = null): List<House> {
        return repository.getHouses(name)
    }
}
