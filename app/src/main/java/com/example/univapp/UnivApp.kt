package com.example.univapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.univapp.ui.nav.AppNavHost

@Composable
fun UnivApp() {
    val navController = rememberNavController()
    AppNavHost(nav = navController)
}
