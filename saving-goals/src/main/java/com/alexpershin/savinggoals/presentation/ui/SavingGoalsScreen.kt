package com.alexpershin.savinggoals.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexpershin.savinggoals.R
import com.alexpershin.savinggoals.presentation.SavingGoalsUiContract
import com.alexpershin.savinggoals.presentation.SavingGoalsUiContract.UiEvents
import com.alexpershin.savinggoals.presentation.SavingGoalsUiContract.UiState
import com.alexpershin.savinggoals.presentation.SavingGoalsViewModel
import com.alexpershin.savinggoals.presentation.model.SavingGoalUiModel
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.components.toolbar.Toolbars
import com.alexpershin.ui.spacings
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SavingGoalsScreen(
    viewModel: SavingGoalsUiContract.ViewModel = hiltViewModel<SavingGoalsViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onUiEvent(UiEvents.BackPressed)
    }

    SavingGoalsContent(
        uiState,
        viewModel::onUiEvent
    )
}


@Composable
fun SavingGoalsContent(
    uiState: UiState,
    uiAction: (UiEvents) -> Unit,
) {
    Scaffold(
        topBar = {
            Components.toolbars.TopAppBar(
                title = stringResource(R.string.top_up_saving_goals_title),
                navigationIcon = Toolbars.NavigationIcon.Back {
                    uiAction(UiEvents.BackPressed)
                }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { uiAction(UiEvents.PullToRefresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                when {
                    uiState.isLoading -> {
                        Components.progress.FullScreenProgress()
                    }

                    uiState.showConfirmationDialog -> {
                        ConfirmationDialog(uiState.roundUpAmount) {
                            uiAction(UiEvents.DialogConfirmed)
                        }
                    }

                    uiState.error is UiState.ErrorType.Content -> {
                        ErrorSection(uiState.error.errorMessageRes) {
                            uiAction(UiEvents.RetryClicked)
                        }
                    }

                    uiState.error is UiState.ErrorType.Dialog -> {
                        ErrorDialog(uiState.error.errorMessageRes) {
                            uiAction(UiEvents.DialogDismissed)
                        }
                    }
                }
                SavingGoalItems(uiState.savingGoalItems, uiAction)
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    amount: String,
    onConfirm: () -> Unit
) {
    Components.dialogs.AlertDialog(
        title = stringResource(R.string.dialog_title_congratulations),
        body = stringResource(R.string.text_money_has_been_added_to_your_goal, amount),
        confirmText = stringResource(R.string.button_ok),
        onConfirm = onConfirm
    )
}

@Composable
private fun ErrorSection(errorMessage: Int, onRetryClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Components.text.Text(
            text = stringResource(id = errorMessage),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = MaterialTheme.spacings.medium),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Components.buttons.ContainedButton(
            text = stringResource(R.string.button_try_again),
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            padding = PaddingValues(
                horizontal = MaterialTheme.spacings.medium,
                vertical = MaterialTheme.spacings.large,
            )
        )
    }
}

@Composable
private fun ErrorDialog(errorMessage: Int, onConfirm: () -> Unit) {
    Components.dialogs.AlertDialog(
        title = stringResource(R.string.dialog_title_error_something_went_wrong),
        body = stringResource(id = errorMessage),
        confirmText = stringResource(id = R.string.button_ok),
        onConfirm = onConfirm
    )
}


@Composable
private fun CreateSavingGoalButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Components.buttons.ContainedButton(
        text = stringResource(R.string.button_create_saving_goal),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        padding = PaddingValues(
            horizontal = MaterialTheme.spacings.medium,
            vertical = MaterialTheme.spacings.large,
        )
    )
}

@Composable
private fun SavingGoalItems(
    items: ImmutableList<SavingGoalUiModel>,
    uiAction: (UiEvents) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(
            items = items,
            key = { it.id }
        ) { model ->
            SavingGoalCard(
                onClick = { uiAction(UiEvents.CardClicked(model.id)) },
                modifier = Modifier.padding(MaterialTheme.spacings.medium),
                model = model
            )
        }
        item {
            CreateSavingGoalButton(onClick = {
                uiAction(UiEvents.CreateSavingGoalClicked)
            })
        }
    }
}