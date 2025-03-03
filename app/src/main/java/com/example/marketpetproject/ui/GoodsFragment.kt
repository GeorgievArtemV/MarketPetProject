package com.example.marketpetproject.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.marketpetproject.databinding.FragmentGoodsBinding
import com.example.marketpetproject.model.Goods
import com.example.marketpetproject.ui.utility.GoodsViewModelFactory
import com.example.marketpetproject.ui.utility.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Экран "Детали товара". В зависимости от клика пользователя, динамически собирается. При клике на кнопку putGoodsInCart
//происходит отправка данных в базу данных (Room), через VM

@AndroidEntryPoint
class GoodsFragment : Fragment() {
    private lateinit var binding: FragmentGoodsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoodsBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        //Получаем bundle и парсим его
        val parsingId = bundle!!.getParcelable<Goods>("Id")
        //Создание вью модельки через фабрику
        val vm: GoodsViewModel by activityViewModels<GoodsViewModel>(
            extrasProducer = {
                defaultViewModelCreationExtras.withCreationCallback<GoodsViewModelFactory> {
                    it.create(parsingId!!.id)
                }
            })

        vm.textLiveData.observe(viewLifecycleOwner) {
            binding.delivery.text = it
        }

        vm.goodsLiveData.observe(viewLifecycleOwner) {
            binding.imageDetailsItemPager.adapter = ImageAdapter(it?.images)
            binding.circleIndicator.setViewPager(binding.imageDetailsItemPager)
            binding.titleDetails.text = it?.title
            binding.ratingBar.rating = it?.rating!!.toFloat()
            binding.rating.text = it.rating.toString()
            binding.priceCard.text = it.price.toString()
            binding.priceWithoutDiscount.text = String.format(
                "%.2f",
                it.price!! * (100 + it.discountPercentage!!) / 100
            ) //переделать
            binding.description.text = it.description
            binding.ratingBar2.rating = it.rating.toFloat()
            binding.rating2.text = it.rating.toString()
            binding.ratingReview1.rating = it.reviews[0].rating.toFloat()
            binding.nameReviewer1.text = it.reviews[0].reviewerName
            binding.reviewerComment1.text = it.reviews[0].comment
            binding.ratingReview2.rating = it.reviews[1].rating.toFloat()
            binding.nameReviewer2.text = it.reviews[1].reviewerName
            binding.reviewerComment2.text = it.reviews[1].comment
            binding.ratingReview3.rating = it.reviews[2].rating.toFloat()
            binding.nameReviewer3.text = it.reviews[2].reviewerName
            binding.reviewerComment3.text = it.reviews[2].comment
        }
        // При нажатии происходит отправка данных в базу данных
        binding.putGoodsInCart.setOnClickListener {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                vm.insertData()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.viewModelStore?.clear()
    }

    //Функция расширение под bundle для нормального парсинга
    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}
