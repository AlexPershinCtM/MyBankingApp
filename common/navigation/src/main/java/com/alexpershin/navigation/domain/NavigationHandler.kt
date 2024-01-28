package com.alexpershin.navigation.domain

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface NavigationHandler {

    val currentRoute: MutableSharedFlow<NavigationCommand>

    suspend fun navigate(newDestination: NavigationCommand)
}




