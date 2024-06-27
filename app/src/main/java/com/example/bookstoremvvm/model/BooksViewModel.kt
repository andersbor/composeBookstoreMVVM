package com.example.bookstoremvvm.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.bookstoremvvm.repository.BooksRepository

class BooksViewModel: ViewModel() {
    private val repository = BooksRepository()
    val booksFlow: State<List<Book>> = repository.booksFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow
    val reloadingFlow: State<Boolean> = repository.isLoadingBooks

    init {
        reload()
    }

    fun reload() {
        repository.getBooks()
    }

    fun add(book: Book) {
        repository.add(book)
    }

    fun update(bookId: Int, book: Book) {
        repository.update(bookId, book)
    }

    fun remove(book: Book) {
        repository.delete(book.id)
    }

    // TODO sorting + filtering
}