package com.example.myapp11.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp11.domain.model.Book
import com.example.myapp11.domain.usecase.GetBookByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface BookDetailUiState {
    object Loading : BookDetailUiState
    data class Success(val book: Book) : BookDetailUiState
    data class Error(val message: String) : BookDetailUiState
}

class BookDetailViewModel(
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailUiState>(BookDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadBook(id: Int = 1) {
        viewModelScope.launch {
            _uiState.value = BookDetailUiState.Loading
            try {
                val book = getBookByIdUseCase(id)
                _uiState.value = BookDetailUiState.Success(book)
            } catch (e: Exception) {
                _uiState.value = BookDetailUiState.Error(e.localizedMessage ?: "Error loading book")
            }
        }
    }
}
