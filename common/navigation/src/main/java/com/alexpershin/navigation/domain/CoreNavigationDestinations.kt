package com.alexpershin.navigation.domain

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface CoreNavigationDestinations : NavigationCommand {

    object Feed : CoreNavigationDestinations {
        override val arguments: List<NamedNavArgument> = listOf()
        override val route: String = "feed"
    }

    data class SavingGoals(val amount: Double) : CoreNavigationDestinations {

        override val arguments: List<NamedNavArgument> = navArguments

        override val route: String = "savingGoals/$amount"

        companion object {
            const val KEY_AMOUNT = "amount"
            const val route: String = "savingGoals/{$KEY_AMOUNT}"

            val navArguments: List<NamedNavArgument> = listOf(
                navArgument(KEY_AMOUNT) {
                    type = NavType.FloatType
                }
            )

            fun navigate(amount: Double): NavigationCommand = SavingGoals(amount)
        }
    }
}