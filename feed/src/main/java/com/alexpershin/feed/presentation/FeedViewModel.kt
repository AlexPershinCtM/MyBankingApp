package com.alexpershin.feed.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alexpershin.core.extentions.parseToString
import com.alexpershin.core.network.StarlingException
import com.alexpershin.feed.R
import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import com.alexpershin.feed.presentation.mapper.FeedUiModelMapper
import com.alexpershin.navigation.domain.NavigationDestinations
import com.alexpershin.navigation.domain.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
internal class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val roundUpUseCase: RoundUpUseCase,
    private val feedUiModelMapper: FeedUiModelMapper,
    private val navigationHandler: NavigationHandler

) : FeedUiContract.ViewModel() {

    private val TAG = FeedViewModel::class.simpleName

    override val _uiState: MutableStateFlow<FeedUiContract.UiState> =
        MutableStateFlow(initialState())

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleFailure(throwable)
    }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            fetchFeed()
        }
    }

    private suspend fun fetchFeed() {
        updateUiState { it.copy(isLoading = true) }
        val feedItemsResult = getFeedUseCase.execute()

        feedItemsResult
            .onSuccess { items ->
                val roundUpAmount = roundUpUseCase.execute(items.map { it.amount })
                val mappedItems = items.map { feedUiModelMapper.map(it) }.toPersistentList()
                updateUiState {
                    it.copy(
                        roundUpAmount = roundUpAmount.parseToString(),
                        isLoading = false,
                        feedItems = mappedItems,
                        errorMessage = null
                    )
                }
            }
            .onFailure {
                handleFailure(it)
            }
    }

    private fun handleFailure(throwable: Throwable) {
        Log.e(TAG, throwable.message.toString())

        val errorMessage = when (throwable) {
            is UnknownHostException -> {
                R.string.error_no_internet
            }

            else -> {
                R.string.generic_error
            }
        }

        updateUiState {
            it.copy(
                errorMessage = errorMessage,
                isLoading = false
            )
        }
    }

    override fun onUiEvent(event: FeedUiContract.UiEvents) {
        when (event) {
            is FeedUiContract.UiEvents.ItemClicked -> {
                handleClickedItem(event)
            }

            is FeedUiContract.UiEvents.BannerClicked -> {
                viewModelScope.launch {
                    val amountToRoundUp = uiState.value.roundUpAmount.toDouble()
                    navigationHandler.navigate(
                        NavigationDestinations.SavingGoals.navigate(
                            amountToRoundUp
                        )
                    )
                }
            }

            is FeedUiContract.UiEvents.BackPressed -> {
                viewModelScope.launch {
                    navigationHandler.navigate(NavigationDestinations.Back)
                }
            }

            FeedUiContract.UiEvents.RetryClicked -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    fetchFeed()
                }
            }

            FeedUiContract.UiEvents.PullToRefresh -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    fetchFeed()
                }
            }
        }
    }

    private fun handleClickedItem(event: FeedUiContract.UiEvents.ItemClicked) {
        val clickedItem = _uiState.value.feedItems.firstOrNull { it.id == event.id }
        Log.i(TAG, "Clicked: $clickedItem")
    }

    private fun initialState(): FeedUiContract.UiState {
        return FeedUiContract.UiState(
            roundUpAmount = "",
            errorMessage = null,
            isLoading = true,
            feedItems = persistentListOf()
        )
    }
}