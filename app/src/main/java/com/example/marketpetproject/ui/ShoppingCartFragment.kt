package com.example.marketpetproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import com.example.marketpetproject.databinding.FragmentShoppingCartBinding
import com.example.marketpetproject.di.modules.db.AppDb
import com.example.marketpetproject.ui.utility.CartAdapter
import com.example.marketpetproject.ui.utility.onItemClick
import dagger.hilt.android.AndroidEntryPoint

//Экран "Корзина". Пользователь решает оплатить товар или удалить из корзины реализованно с поощью интерфейса onItemClick/Нажатием на кнопку и
// .notifyItemRemoved()

@AndroidEntryPoint
class ShoppingCartFragment : Fragment(), onItemClick {
    private lateinit var binding:FragmentShoppingCartBinding
    private val vm: ShoppingCartViewModel by activityViewModels()
    var adapter:CartAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentShoppingCartBinding.inflate(inflater,container,false)
        vm.cartLiveData.observe(viewLifecycleOwner){
            adapter = CartAdapter(it,this@ShoppingCartFragment)
            binding.recyclerCart.adapter = adapter
        }
        //При нажатии пользователь перемещает элементы из базы данных "Cart" в базу данных "Profile". После нажатия корзина становиться пустой
        binding.payCart.setOnClickListener {
            val db = Room.databaseBuilder(requireContext(), AppDb::class.java, "Profile").build().appDAO()
            vm.insertAllCartData(db)
            adapter!!.notifyItemRangeRemoved(0, adapter!!.cartList.size)
            //adapter!!.notifyDataSetChanged()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        activity?.viewModelStore?.clear()
    }
    //Возможность удалить элемент по индексу
    override fun onItemClick(item: Int) {
        vm.deleteItemCart(item)
        adapter!!.notifyItemRemoved(item)
    }
}