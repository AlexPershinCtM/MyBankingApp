package com.alexpershin.navigation.di

import com.alexpershin.navigation.data.NavigationHandlerImpl
import com.alexpershin.navigation.domain.NavigationHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {

    @Binds
    @Singleton
    fun bindNavigationHandler(iml: NavigationHandlerImpl): NavigationHandler

}