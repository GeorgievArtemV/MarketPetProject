package com.example.marketpetproject.ui

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketpetproject.di.modules.db.AppDAO
import com.example.marketpetproject.di.modules.network.GoodsAPI
import com.example.marketpetproject.model.Details
import com.example.marketpetproject.model.GoodsCart
import com.example.marketpetproject.ui.utility.GoodsViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Вью модель для экрана "Детали товара". В конструктор передаем айдишник товара, чтобы получить дополнительную информацию
// При создании VM, происходит вызов функций для получения данных из SharedPref и API, с последующим присваиванием в MutableLiveData.


@HiltViewModel(assistedFactory = GoodsViewModelFactory::class)
class GoodsViewModel @AssistedInject constructor(
    @Assisted val goodsID: Int,
    private val goodsAPI: GoodsAPI,
    private val appDAO: AppDAO,
    private val pref: SharedPreferences
) : ViewModel() {
    val booleanLiveData = MutableLiveData<Boolean>()
    val goodsLiveData = MutableLiveData<Details>()
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
                val result = goodsAPI.getDetailsGoods(goodsID)
                goodsLiveData.postValue(result.body()!!)
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

    //Функиця добавляет товар в базу данных "Cart"
    fun insertData() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            appDAO.insert(
                GoodsCart(
                    null,
                    goodsLiveData.value!!.id,
                    goodsLiveData.value!!.title,
                    goodsLiveData.value!!.price,
                    goodsLiveData.value!!.discountPercentage,
                    goodsLiveData.value!!.images[0]
                )
            )
        }
    }
}