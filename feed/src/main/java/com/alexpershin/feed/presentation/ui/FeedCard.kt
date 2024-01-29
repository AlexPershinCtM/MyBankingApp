package com.alexpershin.feed.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexpershin.feed.presentation.model.FeedUiModel
import com.alexpershin.feed.presentation.model.FeedUiModel.Sign
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.spacings
import com.alexpershin.ui.theme.MyStarlingAppTheme

@Composable
internal fun FeedCard(
    model: FeedUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Components.cards.ElevatedCard(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacings.medium)
        ) {

            Icon(model.icon, contentDescription = null, Modifier.align(Alignment.CenterStart))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 50.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = model.merchant,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = model.dateTime,
                    modifier = Modifier.padding(top = MaterialTheme.spacings.extraSmall)
                )
            }

            with(model) {
                Text(
                    text = "${sign.value}$currency$amount",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeedCardPreview() {
    MyStarlingAppTheme {
        FeedCard(
            onClick = {},
            model = FeedUiModel(
                icon = Icons.Rounded.AccountCircle,
                amount = "22.50",
                currency = FeedUiModel.CurrencySymbol.GBP,
                merchant = "Gov.uk",
                dateTime = "26 Jan 22:30",
                sign = Sign.Minus,
                id = "",
            )
        )
    }
}
