package com.example.myapp11.domain.repository

import com.example.myapp11.domain.model.Character

interface CharacterRepository {

    suspend fun getCharacters(name: String? = null): List<Character>
    suspend fun getCharacterById(id: Int): Character
}
