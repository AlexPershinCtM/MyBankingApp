package com.alexpershin.feed.presentation

import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.compose.runtime.Immutable
import com.alexpershin.core.ui.base.BaseUiEvent
import com.alexpershin.core.ui.base.BaseUiState
import com.alexpershin.core.ui.base.BaseViewModel
import com.alexpershin.feed.presentation.model.FeedUiModel
import kotlinx.collections.immutable.ImmutableList

interface FeedUiContract {

    abstract class ViewModel : BaseViewModel<UiEvents, UiState>()

    @Immutable
    data class UiState(
        val roundUpAmount: String,
        val isLoading: Boolean,
        @StringRes val errorMessage: Int?,
        val feedItems: ImmutableList<FeedUiModel>,
    ) : BaseUiState

    sealed interface UiEvents : BaseUiEvent {
        object RetryClicked : UiEvents
        object BackPressed : UiEvents
        object PullToRefresh : UiEvents
        object BannerClicked : UiEvents
        data class ItemClicked(val id: String) : UiEvents
    }

}