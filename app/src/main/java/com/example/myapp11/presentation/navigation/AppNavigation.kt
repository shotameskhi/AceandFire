package com.example.myapp11.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapp11.presentation.books.BookDetailScreen
import com.example.myapp11.presentation.books.BookScreen
import com.example.myapp11.presentation.characters.CharacterScreen
import com.example.myapp11.presentation.details.CharacterDetailsScreen
import com.example.myapp11.presentation.houses.HouseDetailScreen
import com.example.myapp11.presentation.houses.HouseScreen

sealed class BottomTab(val route: String, val title: String) {
    object Characters : BottomTab("characters", "Characters")
    object Books : BottomTab("books", "Books")
    object Houses : BottomTab("houses", "Houses")
}

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabs = listOf(BottomTab.Characters, BottomTab.Books, BottomTab.Houses)
    val showBottomBar = tabs.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(text = if (tab == BottomTab.Characters) "👤" else if (tab == BottomTab.Books) "📚" else "🏰") },
                            label = { Text(tab.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.Characters.route) {
                CharacterScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = onToggleTheme,
                    onDetailsClick = { name ->
                        navController.navigate("character_details/$name")
                    }
                )
            }

            composable("character_details/{name}") {
                val name = it.arguments?.getString("name") ?: ""
                CharacterDetailsScreen(characterName = name)
            }

            composable(BottomTab.Books.route) {
                BookScreen(
                    onBookClick = { id ->
                        navController.navigate("book_details/$id")
                    }
                )
            }

            composable(
                route = "book_details/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("id") ?: 1
                BookDetailScreen(bookId = id)
            }

            composable(BottomTab.Houses.route) {
                HouseScreen(
                    onHouseClick = { id ->
                        navController.navigate("house_details/$id")
                    }
                )
            }

            composable(
                route = "house_details/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("id") ?: 378
                HouseDetailScreen(houseId = id)
            }
        }
    }
}
