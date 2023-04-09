package com.example.bookfinder.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R

@Composable
fun HomeScreen(onLogout: () -> Unit){
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ){
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.my_favorites),
                            color = MaterialTheme.colors.onSurface
                        )
                    },
                    actions = {
                        MoreAction(
                            onLogoutClicked = {
                                onLogout()
                            }
                        )
                    }

                )
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background).padding(it),
        contentAlignment = Alignment.Center
        ) {

        Image(painter = painterResource(id = R.drawable.logo_no_background), null)
    }
    }

}

@Composable
fun MoreAction(onLogoutClicked: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colors.onSurface
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onLogoutClicked()
                }
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}