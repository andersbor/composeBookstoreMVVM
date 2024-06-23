package com.example.bookstoremvvm.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookstoremvvm.model.Book

@Composable
fun BookAdd(modifier: Modifier = Modifier, onAdd: (Book) -> Unit, onNavigateBack: () -> Unit) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO a lot ...
            Text(text = "Add a book") // TODO style heading
            Button(onClick = { onNavigateBack() }) {
                Text("Back")
            }
            Button(onClick = { onAdd(Book(title = "Kotlin for beginners", price = 10.0)) }) {
                Text("Add")
            }
        }
    }
}