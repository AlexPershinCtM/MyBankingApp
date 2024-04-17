package com.alexpershin.feed.domain.usecase.impl


import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoundUpUseCaseImplTest {

    private val sut = RoundUpUseCaseImpl()

    @Test
    fun testData() = runTest {
        // given
        val listOfMinors = listOf<Long>(
            1,
            2,
            10,
            1234,
            7500,
            9555,
            32400000,
        )

        // when
        val result: Long = sut.execute(listOfMinors)

        // then
        val expected =
            listOf(
                99L,
                98L,
                90L,
                66L,
                100,
                45,
                100
            ).sum()


        assertEquals(
            expected,
            result
        )
    }

    @Test
    fun testCornerCases() = runTest {
        // given
        val listOfMinors = listOf<Long>(
            1,
            10,
            100,
            1000,
        )

        // when
        val result: Long = sut.execute(listOfMinors)

        // then
        val expected =
            listOf(
                99L,
                90,
                100,
                100
            ).sum()

        assertEquals(
            expected,
            result
        )
    }
}