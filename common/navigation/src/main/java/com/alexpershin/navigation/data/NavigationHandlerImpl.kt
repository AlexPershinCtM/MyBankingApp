package com.alexpershin.navigation.data

import com.alexpershin.navigation.domain.NavigationCommand
import com.alexpershin.navigation.domain.NavigationHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

internal class NavigationHandlerImpl @Inject constructor() : NavigationHandler {

    override val currentRoute = MutableSharedFlow<NavigationCommand>()

    override suspend fun navigate(newDestination: NavigationCommand) {
        currentRoute.emit(newDestination)
    }
}
