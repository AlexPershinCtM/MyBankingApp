package com.alexpershin.feed.domain.di

import com.alexpershin.feed.domain.usecase.GetFeedUseCase
import com.alexpershin.feed.domain.usecase.RoundUpUseCase
import com.alexpershin.feed.domain.usecase.impl.GetFeedUseCaseImpl
import com.alexpershin.feed.domain.usecase.impl.RoundUpUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedUseCaseModule {

    @Provides
    @Singleton
    internal fun providesGetFeedUseCase(impl: GetFeedUseCaseImpl): GetFeedUseCase = impl

    @Provides
    @Singleton
    internal fun providesRoundUpUseCase(impl: RoundUpUseCaseImpl): RoundUpUseCase = impl
}
