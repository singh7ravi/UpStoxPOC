package com.example.upstoxpoc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upstoxpoc.presentation.screens.DashBoardScreen
import com.example.upstoxpoc.presentation.screens.LoginScreen
import com.example.upstoxpoc.presentation.screens.SignUpScreen


@Composable
fun ScreenNavigation() {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = DashBoard){
        composable(DashBoard){
            DashBoardScreen(navHostController)
        }
        composable(SignUp){
            SignUpScreen(navHostController)
        }
        composable(Login){
            LoginScreen(navHostController)
        }
    }
}

const val Login = "login_screen"
const val DashBoard = "dashboard_screen"
const val SignUp = "signup_screen"