package com.example.bookstoremvvm.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookstoremvvm.model.Book

@Composable
fun BookDetails(
    book: Book,
    modifier: Modifier = Modifier,
    onUpdate: (Book) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    // TODO a lot ...
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text(text = "Book Details") // TODO style heading
            Text(text = book.title) // TODO editable
            Text(text = book.price.toString()) // TODO editable
            Button(onClick = { onNavigateBack() }) {
                Text("Back")
            }
            Button(onClick = { onUpdate(book) }) {
                Text("Update")
            }
        }
    }

}

@Preview
@Composable
fun BookDetailsPreview() {
    BookDetails(
        book = Book(title = "Kotlin for beginners", price = 10.0)
    )
}