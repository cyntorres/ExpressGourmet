package com.example.expressgourmet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expressgourmet.ui.screens.HomeUserScreen
import com.example.expressgourmet.ui.screens.LoginScreen
import com.example.expressgourmet.ui.screens.RecoverScreen
import com.example.expressgourmet.ui.screens.RegisterScreen
import com.example.expressgourmet.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object RecoverScreen : Screen("recover_screen")
    object HomeUserScreen : Screen("home_user_screen")
}

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(onStartClick = {
                navController.navigate(Screen.RegisterScreen.route) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                onLoginClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onRegistroClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
            )
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onRegistroClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },

                onRecoverClick = {
                    navController.navigate(Screen.RecoverScreen.route)
                },

                onLoginSuccess = {
                    navController.navigate(Screen.HomeUserScreen.route)
                }
            )
        }

        composable(Screen.RecoverScreen.route) {
            RecoverScreen(
                onLoginClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                }
            })
        }

        // ... otros composables
        composable(Screen.HomeUserScreen.route) {
            // Aquí debes llamar a la función de la UI, no a la clase Screen
            HomeUserScreen()
        }

    }
}
