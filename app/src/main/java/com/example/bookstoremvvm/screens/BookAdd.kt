package com.example.bookstoremvvm.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookstoremvvm.model.Book

@Composable
fun BookAdd(
    modifier: Modifier = Modifier,
    addBook: (Book) -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO a lot ...
            Text(text = "Add a book") // TODO style heading
            OutlinedTextField(onValueChange = { title = it },
                value = title,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") })
            OutlinedTextField(onValueChange = { priceStr = it },
                value = priceStr,
                // https://medium.com/@GkhKaya00/exploring-keyboard-types-in-kotlin-jetpack-compose-ca1f617e1109
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Price") })
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { navigateBack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    // TODO validation
                    val book = Book(title = title, price = priceStr.toDouble())
                    addBook(book)
                    navigateBack()
                }) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview
@Composable
fun BookAddPreview() {
    BookAdd()
}