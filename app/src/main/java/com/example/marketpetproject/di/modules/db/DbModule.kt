package com.example.marketpetproject.di.modules.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

// Реализиация DataBase модуля через Room и Hilt. В интерфейсе AppDAO описаны возможные методы взаимодействия с базой данных.

@Module
@InstallIn(ViewModelComponent::class)
object DbModule {

    @Provides
    fun provideDBCart(@ApplicationContext app: Context): AppDb {
        return Room.databaseBuilder(app, AppDb::class.java, "Cart").build()
    }

    @Provides
    fun provideDAOCart(db: AppDb): AppDAO {
        return db.appDAO()
    }
}