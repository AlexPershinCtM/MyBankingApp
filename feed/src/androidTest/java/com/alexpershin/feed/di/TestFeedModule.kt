package com.alexpershin.feed.di

import com.alexpershin.feed.domain.di.FeedUseCaseModule
import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [FeedUseCaseModule::class]
)
object TestFeedModule {

    @Provides
    @Singleton
    internal fun bindGetFeedUseCase(): GetFeedUseCase = mockk(relaxed = true) {
        coEvery { execute() } returns Result.success(emptyList())
    }

    @Provides
    @Singleton
    internal fun bindRoundUpUseCase(): RoundUpUseCase = mockk(relaxed = true) {
        coEvery { execute(any()) } returns 550
    }
}
