package com.example.ejercicio3.Screen

import androidx.compose.runtime.Composable

@Composable
fun Navigation(){
    val navController = rememberNavController()

    navHost(navController = navController, startDestination = "ScreenA"){
        composable("ScreenA"){
            ScreenA(navController)
        }
        composable("ScreenB"){
            ScreenB(navController)
        }
    }
}