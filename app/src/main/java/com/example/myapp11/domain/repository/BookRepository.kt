package com.example.myapp11.domain.repository

import com.example.myapp11.domain.model.Book

interface BookRepository {
    suspend fun getBooks(name: String? = null): List<Book>
    suspend fun getBookById(id: Int): Book
}
