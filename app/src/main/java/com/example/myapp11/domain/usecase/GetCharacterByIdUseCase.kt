package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.repository.CharacterRepository

class GetCharacterByIdUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Character {
        return repository.getCharacterById(id)
    }
}
