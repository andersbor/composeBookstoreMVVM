package com.example.bookstoremvvm.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookstoremvvm.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookList(
    books: List<Book>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBookSelected: (Book) -> Unit = {},
    onBookDeleted: (Book) -> Unit = {},
    onAdd: () -> Unit = {},
    sortByTitle: (up: Boolean) -> Unit = {},
    sortByPrice: (up: Boolean) -> Unit = {},
) {
    //val scaffoldState = rememberScaffoldState()
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Book list") })
        },
        // TODO in landscape layout, half the button is outside the screen
        // something relating to rememberScaffoldState?
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { onAdd() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            if (errorMessage.isNotEmpty()) {
                Text(text = "Problem: $errorMessage")
            }
            val titleUp = "Title \u2191"
            val titleDown = "Title \u2193"
            val priceUp = "Price \u2191"
            val priceDown = "Price \u2193"
            var sortTitleAscending by remember { mutableStateOf(true) }
            var sortPriceAscending by remember { mutableStateOf(true) }

            Row {
                OutlinedButton(onClick = {
                    sortByTitle(sortTitleAscending)
                    sortTitleAscending = !sortTitleAscending
                }) {
                    Text(text = if (sortTitleAscending) titleDown else titleUp)
                }
                TextButton(onClick = {
                    sortByPrice(sortPriceAscending)
                    sortPriceAscending = !sortPriceAscending
                }) {
                    Text(text = if (sortPriceAscending) priceDown else priceUp)
                }
            }
            val orientation = LocalConfiguration.current.orientation
            val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = modifier.fillMaxSize()
            ) {
                items(books) { book ->
                    Card(modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(), onClick = { onBookSelected(book) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = book.title + ": " + book.price.toString()
                            )
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remove",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable { onBookDeleted(book) }
                            )
                        }
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
        ),
        errorMessage = "Some error message"
    )
}