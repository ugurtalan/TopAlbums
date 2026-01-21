package com.example.top_albums.ui.screens.SplashScreen

import com.example.top_albums.navigation.AppCoordinator

class SplashScreenRouter(
   private val coordinator : AppCoordinator
){
    fun route(route: String){
        coordinator.navigate(route)
    }
}
