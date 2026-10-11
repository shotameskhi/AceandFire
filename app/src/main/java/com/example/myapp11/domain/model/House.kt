package com.example.myapp11.domain.model

data class House(
    val id: Int,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String>,
    val seats: List<String>,
    val founded: String,
    val ancestralWeapons: List<String>
)
