package com.example.ejercicio3.Screen

import ScreenB
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@Composable
fun Navegacion(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "ScreenA"){
        composable("ScreenA"){
            ScreenA(navController)
        }
        composable("ScreenB"){
            ScreenB(navController)
        }
    }
}