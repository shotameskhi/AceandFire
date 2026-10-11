package com.example.myapp11.data.remote.api

import com.example.myapp11.data.remote.dto.BookDto
import com.example.myapp11.data.remote.dto.CharacterDto
import com.example.myapp11.data.remote.dto.HouseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IceAndFireApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50,
        @Query("name") name: String? = null
    ): List<CharacterDto>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int = 583
    ): CharacterDto

    @GET("books")
    suspend fun getBooks(
        @Query("name") name: String? = null
    ): List<BookDto>

    @GET("books/{id}")
    suspend fun getBookById(
        @Path("id") id: Int = 1
    ): BookDto

    @GET("houses")
    suspend fun getHouses(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50,
        @Query("name") name: String? = null
    ): List<HouseDto>

    @GET("houses/{id}")
    suspend fun getHouseById(
        @Path("id") id: Int = 378
    ): HouseDto
}
