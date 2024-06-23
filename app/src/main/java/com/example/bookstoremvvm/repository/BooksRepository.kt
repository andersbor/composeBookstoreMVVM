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
    val booksFlow: MutableState<List<Book>> = mutableStateOf(listOf<Book>())
    val reloadingFlow = mutableStateOf(false)
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
        reloadingFlow.value = true
        bookStoreService.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                reloadingFlow.value = false
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
                reloadingFlow.value = false
                errorMessageFlow.value = t.message?: "No connection to back-end"
                Log.d("APPLE", t.message!!)
            }
        })
    }
}