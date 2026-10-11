package com.example.myapp11.domain.usecase

import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.repository.BookRepository

class GetBookByIdUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Int): Book {
        return repository.getBookById(id)
    }
}
