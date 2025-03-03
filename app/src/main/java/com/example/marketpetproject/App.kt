package com.example.marketpetproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Инициализация hilt
@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}