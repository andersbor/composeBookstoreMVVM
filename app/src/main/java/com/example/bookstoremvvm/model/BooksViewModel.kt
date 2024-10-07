package com.example.bookstoremvvm.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.bookstoremvvm.repository.BooksRepository

class BooksViewModel: ViewModel() {
    private val repository = BooksRepository()
    val books: State<List<Book>> = repository.books
    val errorMessage: State<String> = repository.errorMessage
    // TODO use isLoadingBooks to show loading indicator
    val isLoadingBooks: State<Boolean> = repository.isLoadingBooks

    /*init {
        getBooks()
    }*/

    fun getBooks() {
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

    fun sortBooksByTitle(ascending: Boolean) {
        repository.sortBooksByTitle(ascending)
    }

    fun sortBooksByPrice(ascending: Boolean) {
        repository.sortBooksByPrice(ascending)
    }

    fun filterByTitle(titleFragment: String) {
        repository.filterByTitle(titleFragment)
    }
}