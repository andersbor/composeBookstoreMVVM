package com.example.bookstoremvvm.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookstoremvvm.model.Book

@Composable
fun BookList(
    books: List<Book>,
    modifier: Modifier = Modifier,
    onBookSelected: (Book) -> Unit = {},
    onBookDeleted: (Book) -> Unit = {},
    onAdd: () -> Unit = {} // TODO Floating Action Button, requires Scaffold??
) {
    Scaffold(modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onAdd() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(books) { book ->
                Card(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(), onClick = { onBookSelected(book) }) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = book.title + ": " + book.price.toString()
                    )
                    Button(onClick = { onBookDeleted(book) }) { // TODO icon
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BookListPreview() {
    BookList(
        books = listOf(
            Book(title = "Kotlin for beginners", price = 10.0),
            Book(title = "Jetpack Compose", price = 20.0)
        )
    )
}