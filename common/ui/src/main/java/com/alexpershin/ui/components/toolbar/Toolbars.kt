package com.alexpershin.ui.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar as MaterialTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

object Toolbars {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(
        title: String,
        navigationIcon: NavigationIcon = NavigationIcon.None,
    ) {

        MaterialTopAppBar(
            title = {
                Text(text = title)
            },
            navigationIcon = {
                if (navigationIcon is NavigationIcon.Back) {
                    IconButton(onClick = navigationIcon.onClick) {
                        Icon(navigationIcon.icon, contentDescription = "Back")
                    }
                }
            }
        )
    }

    sealed interface NavigationIcon {
        object None : NavigationIcon
        data class Back(
            val icon: ImageVector = Icons.Filled.ArrowBack,
            val onClick: () -> Unit
        ) : NavigationIcon
    }

}