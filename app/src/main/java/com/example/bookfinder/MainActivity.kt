package com.example.bookfinder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookfinder.screens.categories.CategoriesScreen
import com.example.bookfinder.screens.common.BookList
import com.example.bookfinder.screens.details.BookDetailsScreen
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.util.categories
import com.example.bookfinder.util.mockItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookFinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    categories
                    //BookList(mockItems)
                    //BookDetailsScreen(book = mockItems.get(2))
                    CategoriesScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookFinderTheme {
        BookList(mockItems)
    }
}