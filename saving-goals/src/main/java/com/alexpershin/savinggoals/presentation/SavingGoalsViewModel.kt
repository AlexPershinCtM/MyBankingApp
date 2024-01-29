package com.alexpershin.savinggoals.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alexpershin.core.extentions.parseToString
import com.alexpershin.core.extentions.setScale
import com.alexpershin.core.network.StarlingException
import com.alexpershin.navigation.domain.CoreNavigationDestinations
import com.alexpershin.navigation.domain.NavigationCommand
import com.alexpershin.navigation.domain.NavigationHandler
import com.alexpershin.savinggoals.R
import com.alexpershin.savinggoals.domain.usecase.AddMoneySavingGoalUseCase
import com.alexpershin.savinggoals.domain.usecase.CreateSavingGoalUseCase
import com.alexpershin.savinggoals.domain.usecase.GetSavingGoalsUseCase
import com.alexpershin.savinggoals.presentation.SavingGoalsUiContract.UiEvents
import com.alexpershin.savinggoals.presentation.SavingGoalsUiContract.UiState
import com.alexpershin.savinggoals.presentation.mapper.SavingGoalUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
internal class SavingGoalsViewModel @Inject constructor(
    private val navigationHandler: NavigationHandler,
    private val savedStateHandle: SavedStateHandle,
    private val getSavingGoalsUseCase: GetSavingGoalsUseCase,
    private val addMoneySavingGoalUseCase: AddMoneySavingGoalUseCase,
    private val createSavingGoalUseCase: CreateSavingGoalUseCase,
    private val uiModelMapper: SavingGoalUiModelMapper,
) : SavingGoalsUiContract.ViewModel() {

    private val TAG = SavingGoalsViewModel::class.simpleName

    override val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(initialState())

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleFailure(throwable)
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchSavingGoals()
        }
    }

    private suspend fun fetchSavingGoals() {
        updateUiState { it.copy(isLoading = true) }

        val feedItemsResult = getSavingGoalsUseCase.execute()

        feedItemsResult
            .onSuccess { items ->
                val roundUpAmount = getWeekRoundUpAmount()

                val mappedItems = items.asSequence().map { uiModelMapper.map(it) }.toPersistentList()
                updateUiState {
                    it.copy(
                        savingGoalItems = mappedItems,
                        roundUpAmount = roundUpAmount.parseToString(),
                        isLoading = false,
                        error = null
                    )
                }
            }
            .onFailure {
                handleFailure(it)
            }
    }

    private fun handleFailure(throwable: Throwable) {
        val error = when (throwable) {
            is UnknownHostException -> {
                UiState.ErrorType.Content(
                    R.string.error_no_internet
                )
            }

            is StarlingException.InsufficientFunds -> {
                UiState.ErrorType.Dialog(
                    R.string.error_no_enough_funds
                )
            }

            else -> {
                UiState.ErrorType.Content(
                    R.string.generic_error
                )
            }
        }

        Log.e(TAG, throwable.message.toString())

        updateUiState {
            it.copy(
                error = error,
                isLoading = false
            )
        }
    }

    override fun onUiEvent(event: UiEvents) {
        when (event) {
            is UiEvents.CardClicked -> {
                handleCardClicked(event.id)
            }

            is UiEvents.DialogDismissed -> {
                updateUiState {
                    it.copy(
                        error = null,
                        showConfirmationDialog = false
                    )
                }
            }

            is UiEvents.BackPressed -> {
                viewModelScope.launch {
                    navigationHandler.navigate(NavigationCommand.Back)
                }
            }

            UiEvents.RetryClicked -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    fetchSavingGoals()
                }
            }

            UiEvents.CreateSavingGoalClicked -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    createSavingGoal()
                }
            }

            UiEvents.PullToRefresh -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    fetchSavingGoals()
                }
            }
        }
    }

    private suspend fun createSavingGoal() {
        createSavingGoalUseCase.execute()
        fetchSavingGoals()
    }

    private fun handleCardClicked(goalId: String) {
        val moneyToAdd = getWeekRoundUpAmount()
        val clickedItem =
            requireNotNull(_uiState.value.savingGoalItems.firstOrNull { it.id == goalId })


        viewModelScope.launch(coroutineExceptionHandler) {

            val result =
                addMoneySavingGoalUseCase.execute(clickedItem.id, clickedItem.currency, moneyToAdd)

            result.onSuccess {
                fetchSavingGoals()
                updateUiState {
                    it.copy(
                        showConfirmationDialog = true
                    )
                }
            }
                .onFailure {
                    handleFailure(it)
                }
        }
    }

    private fun getWeekRoundUpAmount(): Double {
        return requireNotNull(
            savedStateHandle.get<Double>(CoreNavigationDestinations.SavingGoals.KEY_AMOUNT)
        ).setScale(2).toDouble()
    }

    private fun initialState(): UiState {
        return UiState(
            roundUpAmount = "",
            error = null,
            isLoading = true,
            showConfirmationDialog = false,
            savingGoalItems = persistentListOf(),
        )
    }
}