package com.alexpershin.savinggoals.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alexpershin.savinggoals.R
import com.alexpershin.savinggoals.presentation.model.SavingGoalUiModel
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.spacings

@Composable
internal fun SavingGoalCard(
    model: SavingGoalUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Components.cards.ElevatedCard(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacings.medium)
        ) {
            Text(
                text = model.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacings.small),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.text_saved_amount,
                        model.currencySymbol,
                        model.saved
                    )
                )
                Text(text = " of ")
                Text(text = "${model.currencySymbol}${model.target}")
            }
        }
    }
}

@Preview
@Composable
private fun SavingGoalPreview() {
    SavingGoalCard(
        onClick = {},
        model = SavingGoalUiModel(
            id = "1",
            name = "Visit Paris",
            saved = "2500",
            target = "5000",
            currency = "GBP",
            currencySymbol = SavingGoalUiModel.CurrencySymbol.GBP,
        )
    )
}
