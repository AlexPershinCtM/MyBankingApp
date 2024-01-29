package com.alexpershin.feed.domain.usecase.impl

import com.alexpershin.core.extentions.setScale
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import javax.inject.Inject
import kotlin.math.ceil

internal class RoundUpUseCaseImpl @Inject constructor() : RoundUpUseCase {
    override suspend fun execute(transactions: List<Double>): Double {
        val sum = transactions.sumOf {
            val roundedUp = ceil(it)
            val difference = roundedUp - it

            difference.setScale(2)
        }

        return sum.toDouble()
    }
}

