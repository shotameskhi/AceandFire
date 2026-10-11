package com.example.myapp11.data.remote.dto

data class BookDto(
    val url: String,
    val name: String,
    val isbn: String,
    val authors: List<String>,
    val numberOfPages: Int,
    val publisher: String,
    val country: String,
    val mediaType: String,
    val released: String,
    val characters: List<String> = emptyList(),
    val povCharacters: List<String> = emptyList()
)
