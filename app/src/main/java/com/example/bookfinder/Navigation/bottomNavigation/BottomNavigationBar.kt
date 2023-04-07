package com.example.bookfinder.Navigation.bottomNavigation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent.copy(alpha = 0.7f)), //TRANSAPARAN YAPAN KISIM,
        backgroundColor = Color.Transparent.copy(alpha = 0.3f),
        elevation = 0.dp,//DO NOT TOUCH
        contentColor = Color.Black,
    ) {

        items.forEach() { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,

                modifier = Modifier
                    .height(80.dp),
                icon = { //Iconlar
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.height(80.dp)
                    ) {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                },
            )
        }
    }
}