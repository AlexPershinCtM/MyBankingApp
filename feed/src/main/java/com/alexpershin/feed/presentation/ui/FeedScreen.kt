package com.alexpershin.feed.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexpershin.feed.R
import com.alexpershin.feed.presentation.FeedUiContract
import com.alexpershin.feed.presentation.FeedUiContract.UiEvents
import com.alexpershin.feed.presentation.FeedUiContract.UiState
import com.alexpershin.feed.presentation.FeedViewModel
import com.alexpershin.feed.presentation.model.FeedUiModel
import com.alexpershin.ui.components.Components
import com.alexpershin.ui.lifecycle.ResumeHandler
import com.alexpershin.ui.spacings
import com.alexpershin.ui.theme.MyStarlingAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun FeedScreen(
    viewModel: FeedUiContract.ViewModel = hiltViewModel<FeedViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onUiEvent(UiEvents.BackPressed)
    }

    ResumeHandler {
        viewModel.onUiEvent(UiEvents.OnResume)
    }

    FeedContent(
        uiState,
        viewModel::onUiEvent
    )
}

@Composable
fun FeedContent(
    uiState: UiState,
    uiAction: (UiEvents) -> Unit,
) {
    Scaffold(
        topBar = {
            Components.toolbars.TopAppBar(
                title = stringResource(R.string.feed_title)
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

                    uiState.errorMessage != null -> {
                        ErrorSection(uiState.errorMessage) {
                            uiAction(UiEvents.RetryClicked)
                        }
                    }

                    uiState.feedItems.isEmpty() -> {
                        FeedEmptySection()
                    }

                    else -> {
                        RoundUpBanner(uiState.roundUpAmount) {
                            uiAction(UiEvents.RoundUpBannerClicked)
                        }

                        FeedItems(uiState.feedItems) { id ->
                            uiAction(UiEvents.ItemClicked(id))
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ErrorSection(errorMessage: Int, onRetryClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                .align(Alignment.BottomCenter)
                .testTag(FeedScreenTestTags.TryAgainButton),
            padding = PaddingValues(
                horizontal = MaterialTheme.spacings.medium,
                vertical = MaterialTheme.spacings.large,
            )
        )
    }
}

@Composable
private fun FeedEmptySection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Components.text.Text(
            text = stringResource(R.string.text_no_transactions),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacings.medium),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun FeedItems(
    feedItems: ImmutableList<FeedUiModel>,
    onItemClick: (id: String) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(
            items = feedItems,
            key = { it.id }
        ) { model ->
            FeedCard(
                onClick = { onItemClick(model.id) },
                modifier = Modifier.padding(MaterialTheme.spacings.medium),
                model = model
            )
        }
    }
}

object FeedScreenTestTags {
    const val RoundUpBanner = "RoundUpBannerTestTag"
    const val TryAgainButton = "TryAgainButtonTestTag"
}


//region Previews

@Composable
@Preview(name = "No internet error")
private fun ErrorFeedContentPreview() {
    MyStarlingAppTheme {
        FeedContent(
            uiState = UiState(
                roundUpAmount = "",
                isLoading = false,
                errorMessage = R.string.error_no_internet,
                feedItems = persistentListOf()
            )
        ) {}
    }
}

@Composable
@Preview(name = "Empty state")
private fun EmptyFeedContentPreview() {
    MyStarlingAppTheme {
        FeedContent(
            uiState = UiState(
                roundUpAmount = "",
                isLoading = false,
                errorMessage = null,
                feedItems = persistentListOf()
            )
        ) {}
    }
}


@Composable
@Preview(name = "Success state")
private fun SuccessFeedContentPreview(@PreviewParameter(FeedContentParamProvider::class) items: ImmutableList<FeedUiModel>) {
    MyStarlingAppTheme {
        FeedContent(
            uiState = UiState(
                roundUpAmount = "0.86",
                isLoading = false,
                errorMessage = null,
                feedItems = persistentListOf<FeedUiModel>(
                    FeedUiModel(
                        id = "",
                        icon = Icons.Rounded.Home,
                        amount = "33.14",
                        currency = FeedUiModel.CurrencySymbol.GBP,
                        sign = FeedUiModel.Sign.Minus,
                        merchant = "Asda",
                        dateTime = "30-01-2024 16:27"
                    )
                )
            )
        ) {}
    }
}
//endregion
