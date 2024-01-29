package com.alexpershin.savinggoals.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.alexpershin.core.ui.base.BaseUiEvent
import com.alexpershin.core.ui.base.BaseUiState
import com.alexpershin.core.ui.base.BaseViewModel
import com.alexpershin.savinggoals.presentation.model.SavingGoalUiModel
import kotlinx.collections.immutable.ImmutableList

interface SavingGoalsUiContract {

    abstract class ViewModel : BaseViewModel<UiEvents, UiState>()

    @Immutable
    data class UiState(
        val roundUpAmount: String,
        val showConfirmationDialog: Boolean,
        val isLoading: Boolean,
        val error: ErrorType?,
        val savingGoalItems: ImmutableList<SavingGoalUiModel>,
    ) : BaseUiState {
        sealed class ErrorType(@StringRes val errorMessageRes: Int) {
            class Dialog(error: Int) : ErrorType(error)
            class Content(error: Int) : ErrorType(error)
        }
    }

    sealed interface UiEvents : BaseUiEvent {
        object DialogDismissed : UiEvents
        object DialogConfirmed : UiEvents
        object RetryClicked : UiEvents
        object CreateSavingGoalClicked : UiEvents
        object BackPressed : UiEvents
        object PullToRefresh : UiEvents
        data class CardClicked(val id: String) : UiEvents
    }

}