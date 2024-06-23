package com.example.bookstoremvvm.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.bookstoremvvm.repository.BooksRepository

class BooksViewModel: ViewModel() {
    private val repository = BooksRepository()
    val booksFlow: State<List<Book>> = repository.booksFlow
    val errorMessageFlow: State<String> = repository.errorMessageFlow
    val reloadingFlow: State<Boolean> = repository.reloadingFlow

    init {
        reload()
    }

    fun reload() {
        repository.getBooks()
    }

    fun add(book: Book) {
        //repository.add(book)
        // TODO
    }

    fun update(book: Book) {
        //repository.update(book)
        // TODO
    }

    fun remove(book: Book) {
        //repository.remove(book)
        // TODO
    }
}