package com.example.top_albums.navigation

import androidx.navigation.NavHostController

class AppCoordinator(
    private val navController: NavHostController
)  {

     fun openList() {
        navController.navigate("list"){
            popUpTo("splash"){
                inclusive = true
            }
        }


    }


}