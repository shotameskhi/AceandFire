package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {

    suspend operator fun invoke(name: String? = null): List<Character> {
        return repository.getCharacters(name)
    }
}
