package com.example.myapp11.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var allBooksList: List<Book> = emptyList()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        loadBooks()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterBooks(query)
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                allBooksList = getBooksUseCase()
                filterBooks(_searchQuery.value)
            } catch (e: Exception) {
                _books.value = emptyList()
            }
        }
    }

    private fun filterBooks(query: String) {
        if (query.isBlank()) {
            _books.value = allBooksList
        } else {
            _books.value = allBooksList.filter { book ->
                book.name.contains(query, ignoreCase = true) ||
                        book.authors.any { it.contains(query, ignoreCase = true) }
            }
        }
    }
}
