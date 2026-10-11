package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.House
import com.example.myapp11.domain.repository.HouseRepository

class GetHouseByIdUseCase(
    private val repository: HouseRepository
) {
    suspend operator fun invoke(id: Int): House {
        return repository.getHouseById(id)
    }
}
