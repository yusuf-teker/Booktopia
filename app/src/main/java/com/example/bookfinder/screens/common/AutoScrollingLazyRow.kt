package com.example.bookfinder.screens.common

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.bookfinder.data.model.remote.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoScrollingLazyRow(
    items: List<Book>,
    modifier: Modifier = Modifier,
    scrollDuration: Int = 3000, // Kaydırma süresi (ms)
    content: @Composable LazyItemScope.(Book) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        while (true) {
            val currentScrollPosition = listState.firstVisibleItemIndex
            val nextScrollPosition = if (currentScrollPosition < items.size - 4) currentScrollPosition + 1 else 0

            coroutineScope.launch {
              listState.animateScrollToItem(nextScrollPosition)
            }
            delay(scrollDuration.toLong())
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
    ) {
        itemsIndexed(items) { index, item ->
            content(item)
        }
    }
}