package com.alexpershin.feed.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.alexpershin.feed.R
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.spacings

@Composable
private fun RoundUpBanner(roundUpAmount: String, onClick: () -> Unit) {
    Components.cards.OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(FeedScreenTestTags.RoundUpBanner)
            .padding(horizontal = MaterialTheme.spacings.medium),
        onClick = onClick
    ) {
        Column {
            Components.text.Text(
                text = stringResource(R.string.round_up_banner_make_your_dream_real),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacings.medium),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Components.text.Text(
                text = stringResource(
                    R.string.round_aup_banner_transfer_to_your_saving_goal,
                    roundUpAmount
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacings.medium),
                textAlign = TextAlign.Center
            )
        }
    }
}
