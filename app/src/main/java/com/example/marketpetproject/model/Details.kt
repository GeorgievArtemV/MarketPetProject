package com.example.marketpetproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Моделька с отзывами, получаем данные с Retrofit

@Parcelize
data class Details(
    val id: Int,
    val title: String?,
    val description: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val rating: Double?,
    val stock: Int?,
    val brand: String?,
    val category: String?,
    val thumbnail: String?,
    val images: List<String>,
    val reviews: List<Reviews>
) : Parcelable

@Parcelize
data class Reviews(
    val rating: Int,
    val comment: String,
    val reviewerName: String
) : Parcelable


