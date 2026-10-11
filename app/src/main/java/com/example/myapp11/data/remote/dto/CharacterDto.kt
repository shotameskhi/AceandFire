package com.example.myapp11.data.remote.dto

data class CharacterDto(
    val url: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val titles: List<String>,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>
)
