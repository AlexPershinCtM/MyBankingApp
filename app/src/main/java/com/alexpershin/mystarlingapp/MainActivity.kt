package com.alexpershin.mystarlingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexpershin.ui.theme.MyStarlingAppTheme
import com.alexpershin.feed.presentation.ui.FeedScreen
import com.alexpershin.navigation.domain.NavigationDestinations
import com.alexpershin.navigation.domain.NavigationHandler
import com.alexpershin.savinggoals.presentation.ui.SavingGoalsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavigationHandler(navController)

            MyStarlingAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationDestinations.Feed.route
                ) {
                    composable(NavigationDestinations.Feed.route) {
                        FeedScreen()
                    }
                    composable(
                        route = NavigationDestinations.SavingGoals.route,
                        arguments = NavigationDestinations.SavingGoals.arguments) {
                        SavingGoalsScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationHandler(navController: NavHostController) {
        LaunchedEffect(navigationHandler.currentRoute) {
            navigationHandler.currentRoute.collectLatest {
                when (it) {
                    is NavigationDestinations.Back -> {
                        handleBackPress(navController)
                    }
                    else -> {
                        navController.navigate(it.route)
                    }
                }
            }
        }
    }

    private fun handleBackPress(navController: NavHostController) {
        if (!navController.popBackStack()) {
            finish()
        }
    }
}
