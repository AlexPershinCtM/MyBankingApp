package com.alexpershin.feed.domain.usecase.impl

import com.alexpershin.feed.domain.model.AmountMinors
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import javax.inject.Inject

/**
 * Implementation of the RoundUpUseCase interface that calculates the total round-up amount
 * for a list of transactions.
 *
 * This implementation calculates the round-up amount for each transaction. It then sums up all
 * the round-up amounts to return the total round-up amount.
 */
internal class RoundUpUseCaseImpl @Inject constructor() : RoundUpUseCase {

    /**
     * Executes the round-up calculation for the given list of transactions.
     *
     * @param transactions List of transaction amounts represented as Long values.
     * @return Total round-up amount calculated from the list of transactions.
     */
    override suspend fun execute(transactions: List<AmountMinors>): AmountMinors {
        // Calculate the sum of round-up amounts for all transactions
        val sum = transactions.sumOf {
            // Calculate the fractional part of the transaction amount
            val fraction = it % 100 // e.g., 1234 -> 34

            // Calculate the round-up amount for the transaction
            val roundUpAmount = 100 - fraction // e.g., 66

            roundUpAmount
        }

        return sum
    }
}
