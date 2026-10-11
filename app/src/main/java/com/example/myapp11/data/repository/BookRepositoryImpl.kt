package com.example.myapp11.data.repository

import com.example.myapp11.data.remote.api.IceAndFireApi
import com.example.myapp11.data.remote.dto.toDomain
import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.repository.BookRepository

class BookRepositoryImpl(
    private val api: IceAndFireApi
) : BookRepository {

    override suspend fun getBooks(name: String?): List<Book> {
        return api.getBooks(name = name).map { it.toDomain() }
    }

    override suspend fun getBookById(id: Int): Book {
        return api.getBookById(id).toDomain()
    }
}
