package com.alexpershin.savinggoals.presentation.mapper

import com.alexpershin.core.extentions.parseToString
import com.alexpershin.core.mapper.BaseModelMapper
import com.alexpershin.savinggoals.domain.model.SavingGoal
import com.alexpershin.savinggoals.presentation.model.SavingGoalUiModel
import javax.inject.Inject

    internal class SavingGoalUiModelMapper @Inject constructor() : BaseModelMapper<SavingGoal, SavingGoalUiModel> {

    override fun map(model: SavingGoal): SavingGoalUiModel = with(model) {
        return SavingGoalUiModel(
            id = id,
            name = name,
            currency = savedCurrency,
            saved = savedAmount.parseToString(),
            target = targetAmount.toInt().toString(),
            currencySymbol = mapCurrencySymbol(targetCurrency)
        )
    }

    private fun mapCurrencySymbol(targetCurrency: String): SavingGoalUiModel.CurrencySymbol {
        return when (targetCurrency) {
            "GBP" -> SavingGoalUiModel.CurrencySymbol.GBP
            "EUR" -> SavingGoalUiModel.CurrencySymbol.EUR
            "USD" -> SavingGoalUiModel.CurrencySymbol.USD
            else -> SavingGoalUiModel.CurrencySymbol.Unknown
        }
    }
}