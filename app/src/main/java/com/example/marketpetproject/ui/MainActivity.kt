package com.example.marketpetproject.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.marketpetproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

//MainActivity приложения, использую паттерн single activity. Навигация происходит пока что через supportFragmentManager.beginTransaction()
//Нужно переехать на navComponent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Вызов функции для создания сплэш скрина
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val navigationMenu: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*val navigationController = findNavController(R.id.navigation)
        val appBar = AppBarConfiguration(setOf(R.id.home,R.id.profile,R.id.cart))
        setupActionBarWithNavController(navigationController,appBar)
        navigationMenu.setupWithNavController(navigationController)*/
        supportFragmentManager.beginTransaction().replace(R.id.placeHolder, CoreFragment())
            .addToBackStack("first").commit()
        navigationMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> supportFragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, CoreFragment()).addToBackStack("first").commit()

                R.id.profile -> supportFragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, ProfileFragment()).addToBackStack("first").commit()

                R.id.cart -> supportFragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, ShoppingCartFragment()).addToBackStack("first")
                    .commit()
            }
            return@setOnItemSelectedListener true
        }
    }
}