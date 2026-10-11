package com.example.myapp11.data.repository

import com.example.myapp11.data.remote.api.IceAndFireApi
import com.example.myapp11.data.remote.dto.toDomain
import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.repository.CharacterRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class CharacterRepositoryImpl(
    private val api: IceAndFireApi
) : CharacterRepository {

    private var cachedCharacters: List<Character> = emptyList()

    override suspend fun getCharacters(name: String?): List<Character> {
        if (cachedCharacters.isEmpty()) {
            cachedCharacters = fetchInitialCharacters()
        }

        if (name.isNullOrBlank()) {
            return cachedCharacters
        }

        val query = name.trim()

        val localMatches = cachedCharacters.filter { char ->
            char.name.contains(query, ignoreCase = true) ||
                    char.aliases.any { it.contains(query, ignoreCase = true) } ||
                    char.titles.any { it.contains(query, ignoreCase = true) }
        }

        if (localMatches.isNotEmpty()) {
            return localMatches
        }

        return try {
            val apiMatches = api.getCharacters(name = query).map { it.toDomain() }
            apiMatches.ifEmpty {
                cachedCharacters.filter { char ->
                    char.name.contains(query, ignoreCase = true)
                }
            }
        } catch (e: Exception) {
            localMatches
        }
    }

    private suspend fun fetchInitialCharacters(): List<Character> = coroutineScope {
        val deferredPages = (1..8).map { page ->
            async {
                try {
                    api.getCharacters(page = page, pageSize = 50).map { it.toDomain() }
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        val popularIds = listOf(583, 148, 271, 1052, 339, 957, 208, 529, 238, 1303, 1445)
        val deferredPopular = async {
            popularIds.mapNotNull { id ->
                try {
                    api.getCharacterById(id).toDomain()
                } catch (e: Exception) {
                    null
                }
            }
        }

        val pagesResults = deferredPages.flatMap { it.await() }
        val popularResults = deferredPopular.await()

        (popularResults + pagesResults)
            .distinctBy { it.name }
            .filter { it.name.isNotBlank() }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return api.getCharacterById(id).toDomain()
    }
}
