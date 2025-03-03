package com.example.marketpetproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketpetproject.di.modules.db.AppDAO
import com.example.marketpetproject.model.GoodsCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//Вью модель для экрана "Корзина". При иницилизации VM получаем данные по методу getCartData()
//Нужно доделать анимации

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
     private val cartDAO: AppDAO,
) : ViewModel() {

    val cartLiveData = MutableLiveData<List<GoodsCart>>()
    val emptyCart = MutableLiveData<Boolean>() //Если корзина пуста, то показать верстку, нужно доделать...

    init {
        getCartData()
    }

    fun deleteItemCart(int: Int) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            cartDAO.delete(cartLiveData.value!![int])
        }
        getCartData()
    }

    private fun getCartData() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            cartLiveData.postValue(cartDAO.getAll())
        }
    }
    //Проходимся по массиву и отправлем каждый элемнт в базу данных
    fun insertAllCartData(db:AppDAO) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            for (value in cartDAO.getAll()) {
                db.insert(value)
                cartDAO.delete(value)
            }
        }
        getCartData()
    }
}