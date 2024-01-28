package com.alexpershin.navigation.domain

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NavigationDestinations {

    object Back : NavigationCommand {
        override val arguments: List<NamedNavArgument> = listOf()
        override val route: String = ""
    }

    object Feed : NavigationCommand {
        override val arguments: List<NamedNavArgument> = listOf()
        override val route: String = "feed"
    }

    object SavingGoals {
        const val KEY_AMOUNT = "amount"
        const val route: String = "savingGoals/{$KEY_AMOUNT}"

        val arguments: List<NamedNavArgument> = listOf(
            navArgument(KEY_AMOUNT) {
                type = NavType.FloatType
            }
        )

        fun navigate(amount: Double) = object : NavigationCommand {
            override val arguments = SavingGoals.arguments


            override val route: String = "savingGoals/$amount"
        }
    }

}