package com.alexpershin.ui.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.theme.MyStarlingAppTheme
import androidx.compose.material3.OutlinedCard as MaterialOutlinedCard
import androidx.compose.material3.ElevatedCard as MaterialElevatedCard

object Cards {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OutlinedCard(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        MaterialOutlinedCard(
            modifier = modifier,
            onClick = onClick,
            content = content
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ElevatedCard(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        MaterialElevatedCard(
            modifier = modifier,
            onClick = onClick,
            content = content
        )
    }
}

@Preview
@Composable
internal fun OutlinedCardPreview() {
    MyStarlingAppTheme {
        Components.cards.OutlinedCard(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Preview
@Composable
internal fun ElevatedCardPreview() {
    MyStarlingAppTheme {
        Components.cards.ElevatedCard(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}
