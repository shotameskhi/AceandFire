package com.example.myapp11.data.remote.dto

import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.model.Character
import com.example.myapp11.domain.model.House

fun CharacterDto.toDomain() = Character(
    name = name,
    gender = gender,
    culture = culture,
    born = born,
    titles = titles,
    aliases = aliases,
    tvSeries = tvSeries,
    playedBy = playedBy,
)

fun BookDto.toDomain(): Book {
    val id = url.substringAfterLast("/").toIntOrNull() ?: 1
    return Book(
        id = id,
        name = name,
        isbn = isbn,
        authors = authors,
        numberOfPages = numberOfPages,
        publisher = publisher,
        country = country,
        mediaType = mediaType,
        released = released
    )
}

fun HouseDto.toDomain(): House {
    val id = url.substringAfterLast("/").toIntOrNull() ?: 378
    return House(
        id = id,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        founded = founded,
        ancestralWeapons = ancestralWeapons
    )
}
