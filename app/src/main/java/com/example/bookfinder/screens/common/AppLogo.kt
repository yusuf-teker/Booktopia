package com.example.bookfinder.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R

@Composable
fun AppLogo() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 48.dp, bottom = 32.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_no_background), contentDescription = null,
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(0.6f)
        )

    }
}