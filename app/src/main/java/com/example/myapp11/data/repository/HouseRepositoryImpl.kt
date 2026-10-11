package com.example.myapp11.data.repository

import com.example.myapp11.data.remote.api.IceAndFireApi
import com.example.myapp11.data.remote.dto.toDomain
import com.example.myapp11.domain.model.House
import com.example.myapp11.domain.repository.HouseRepository

class HouseRepositoryImpl(
    private val api: IceAndFireApi
) : HouseRepository {

    override suspend fun getHouses(name: String?): List<House> {
        return api.getHouses(name = name).map { it.toDomain() }
    }

    override suspend fun getHouseById(id: Int): House {
        return api.getHouseById(id).toDomain()
    }
}
