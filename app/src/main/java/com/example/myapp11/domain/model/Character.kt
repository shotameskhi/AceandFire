package com.example.myapp11.domain.model

data class Character(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val titles: List<String>,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)