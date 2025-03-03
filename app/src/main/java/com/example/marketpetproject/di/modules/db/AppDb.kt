package com.example.marketpetproject.di.modules.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marketpetproject.model.GoodsCart
import dagger.Provides

@Database(entities = [GoodsCart::class], version = 1)

abstract class AppDb: RoomDatabase() {
    abstract fun appDAO(): AppDAO
}