package com.example.bookstoremvvm.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.bookstoremvvm.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BooksRepository {
    private val baseUrl = "https://anbo-restbookquerystring.azurewebsites.net/api/"
    // the specific (collection) part of the URL is on the individual methods in the interface BookstoreService

    private val bookStoreService: BookStoreService
    val booksFlow: MutableState<List<Book>> = mutableStateOf(listOf())
    val isLoadingBooks = mutableStateOf(false)
    val errorMessageFlow = mutableStateOf("")

    init {
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            //.addConverterFactory(KotlinJsonAdapterFactory)
            //.addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi, added to Gradle dependencies
            .build()
        bookStoreService = build.create(BookStoreService::class.java)
        getBooks()
    }

    fun getBooks() {
        isLoadingBooks.value = true
        bookStoreService.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                isLoadingBooks.value = false
                if (response.isSuccessful) {
                    //Log.d("APPLE", response.body().toString())
                    val bookList: List<Book>? = response.body()
                    booksFlow.value = bookList ?: emptyList()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                isLoadingBooks.value = false
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun add(book: Book) {
        bookStoreService.saveBook(book).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    getBooks()
                    errorMessageFlow.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun delete(id: Int) {
        Log.d("APPLE", "Delete: $id")
        bookStoreService.deleteBook(id).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Delete: " + response.body())
                    errorMessageFlow.value = ""
                    getBooks()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", "Not deleted: $message")
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", "Not deleted $message")
            }
        })
    }

    fun update(bookId: Int, book: Book) {
        Log.d("APPLE", "Update: $bookId $book")
        bookStoreService.updateBook(bookId, book).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    errorMessageFlow.value = ""
                    Log.d("APPLE", "update successful")
                    getBooks()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageFlow.value = message
                    Log.d("APPLE", "Update $message")
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessageFlow.value = message
                Log.d("APPLE", "Update $message")
            }
        })
    }

    fun sortBooksByTitle(ascending: Boolean) {
        Log.d("APPLE", "Sort by title")
        if (ascending)
            booksFlow.value = booksFlow.value.sortedBy { it.title }
        else
            booksFlow.value = booksFlow.value.sortedByDescending { it.title }
    }

    fun sortBooksByPrice(ascending: Boolean) {
        Log.d("APPLE", "Sort by price")
        if (ascending)
            booksFlow.value = booksFlow.value.sortedBy { it.price }
        else
            booksFlow.value = booksFlow.value.sortedByDescending { it.price }

    }
}