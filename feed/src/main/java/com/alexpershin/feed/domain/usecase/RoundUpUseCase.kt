package com.alexpershin.feed.domain.usecase

import com.alexpershin.feed.domain.model.AmountMinors


internal interface RoundUpUseCase {
    suspend fun execute(transactions: List<AmountMinors>): AmountMinors
}