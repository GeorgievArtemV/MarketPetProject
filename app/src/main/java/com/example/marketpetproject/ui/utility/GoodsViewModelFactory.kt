package com.example.marketpetproject.ui.utility

import com.example.marketpetproject.ui.GoodsViewModel
import dagger.assisted.AssistedFactory

//Фабрика для ViewModel, в которую передаем индекс элемента

@AssistedFactory
interface GoodsViewModelFactory {
    fun create(int: Int): GoodsViewModel
}