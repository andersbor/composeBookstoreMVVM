package com.example.bookstoremvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstoremvvm.model.Book
import com.example.bookstoremvvm.model.BooksViewModel
import com.example.bookstoremvvm.screens.BookAdd
import com.example.bookstoremvvm.screens.BookDetails
import com.example.bookstoremvvm.screens.BookList
import com.example.bookstoremvvm.ui.theme.BookstoreMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookstoreMVVMTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                //}
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: BooksViewModel = viewModel() // persistence
    val books = viewModel.booksFlow.value

    NavHost(navController = navController, startDestination = NavRoutes.BookList.route) {
        composable(NavRoutes.BookList.route) {
            BookList(
                modifier = modifier,
                books = books,
                onBookSelected = { navController.navigate(NavRoutes.BookDetails.route) },
                onBookDeleted = { book -> viewModel.remove(book) },
                onAdd = { navController.navigate(NavRoutes.BookAdd.route) })
        }
        composable(NavRoutes.BookDetails.route) {
            BookDetails(modifier = modifier,
                book = Book(title = "Book", price = 10.0), // TODO get from parameter
                onUpdate = { book -> viewModel.update(book) },
                onNavigateBack = { navController.popBackStack() })
        }
        composable(NavRoutes.BookAdd.route) {
            BookAdd(modifier = modifier,
                onAdd = { book -> viewModel.add(book) },
                onNavigateBack = { navController.popBackStack() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListPreview() {
    BookstoreMVVMTheme {
        MainScreen()
    }
}