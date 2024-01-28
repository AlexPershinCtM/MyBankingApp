package com.alexpershin.navigation.domain

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val arguments : List<NamedNavArgument>
    val route : String
}