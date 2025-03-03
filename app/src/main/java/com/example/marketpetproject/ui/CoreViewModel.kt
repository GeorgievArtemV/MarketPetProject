package com.example.marketpetproject.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketpetproject.di.modules.network.GoodsAPI
import com.example.marketpetproject.model.Goods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Вью Модель для экрана "Основной". При создании VM, происходит вызов функций для получения данных из SharedPref и API,
// с последующим присваиванием в MutableLiveData.

@HiltViewModel
class CoreViewModel @Inject constructor(
    private val goodsAPI: GoodsAPI,
    private val pref: SharedPreferences
) : ViewModel() {

    val booleanLiveData = MutableLiveData<Boolean>()
    val listLiveData = MutableLiveData<MutableList<Goods>>()
    val textLiveData = MutableLiveData<String>()

    init {
        getGoodsAPI()
        getPref()
    }

    fun getGoodsAPI() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        val scope = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)
        scope.launch {
            try {
                val result = goodsAPI.getGoods(194, 0)
                listLiveData.postValue(result.body()!!.products.toMutableList())
                booleanLiveData.postValue(true)
            } catch (e: Exception) {
                booleanLiveData.postValue(false)
            }
        }
    }

    fun getPref() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        val scope = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)
        scope.launch {
            textLiveData.postValue(pref.getString("PVZ", "не определено"))
        }
    }

    // Функция вызывается если пользователь меняет текстовое поле адреса, дальше происходит сохжранение в SharedPref
    fun insertPref(string: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            pref.edit()?.putString("PVZ", string)?.commit()
        }
    }
}