package com.example.myapp11.domain.repository

import com.example.myapp11.domain.model.House

interface HouseRepository {
    suspend fun getHouses(name: String? = null): List<House>
    suspend fun getHouseById(id: Int): House
}
