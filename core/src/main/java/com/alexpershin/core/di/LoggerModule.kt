package com.alexpershin.core.di

import com.alexpershin.core.logging.ErrorLogger
import com.alexpershin.core.logging.impl.ErrorLoggerImpl
import com.alexpershin.core.preferences.SecurePreferences
import com.alexpershin.core.preferences.impl.SecurePreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LoggerModule {

    @Binds
    fun bindErrorLogger(impl: ErrorLoggerImpl): ErrorLogger

}

