package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.repository.BookRepository

class GetBooksUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(name: String? = null): List<Book> {
        return repository.getBooks(name)
    }
}
