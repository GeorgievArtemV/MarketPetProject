package com.example.marketpetproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketpetproject.di.modules.db.AppDAO
import com.example.marketpetproject.model.GoodsCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Вью модель для экрана "Заказы". Получаем данные с базы данных и делаем .reversed() т.к нужно показывать от новых к старым
// (что-то типа сортировка по дате покупки)

class ProfileViewModel() : ViewModel() {
    val setLiveData = MutableLiveData<List<GoodsCart>>()
    fun setData(db: AppDAO) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            setLiveData.postValue(db.getAll().reversed())
        }
    }
}
