package com.example.marketpetproject.di.modules.pref

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

// Реализиация SharedPreferences модуля через Hilt.

@Module
@InstallIn(SingletonComponent::class)
object PrefModule {
    @Provides
    fun provideShared(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("Delivery", Context.MODE_PRIVATE)
    }
}