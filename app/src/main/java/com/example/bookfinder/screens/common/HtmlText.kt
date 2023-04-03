package com.example.bookfinder.screens.common

import android.util.TypedValue
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.bookfinder.R
import com.example.bookfinder.ui.theme.BookFinderTheme


@Composable
fun HtmlText(htmlText: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
            setTextColor(resources.getColor(R.color.onSurface,null))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    BookFinderTheme {
        HtmlText(htmlText = "ss")
    }
}