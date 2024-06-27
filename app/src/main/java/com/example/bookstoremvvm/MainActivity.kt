package com.example.bookstoremvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: BooksViewModel = viewModel() // persistence
    val books = viewModel.booksFlow.value
    val errorMessage = viewModel.errorMessageFlow.value

    NavHost(navController = navController, startDestination = NavRoutes.BookList.route) {
        composable(NavRoutes.BookList.route) {
            BookList(
                modifier = modifier,
                books = books,
                errorMessage = errorMessage,
                onBookSelected =
                { book -> navController.navigate(NavRoutes.BookDetails.route + "/${book.id}") },
                onBookDeleted = { book -> viewModel.remove(book) },
                onAdd = { navController.navigate(NavRoutes.BookAdd.route) })
        }
        composable(
            NavRoutes.BookDetails.route + "/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backstackEntry ->
            val bookId = backstackEntry.arguments?.getInt("bookId")
            val book = books.find { it.id == bookId } ?: Book(title = "No book", price = 0.0)
            BookDetails(modifier = modifier,
                book = book,
                onUpdate = { id: Int, book: Book -> viewModel.update(id, book) },
                onNavigateBack = { navController.popBackStack() })
        }
        composable(NavRoutes.BookAdd.route) {
            BookAdd(modifier = modifier,
                addBook = { book -> viewModel.add(book) },
                navigateBack = { navController.popBackStack() })
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