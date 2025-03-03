package com.example.marketpetproject.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Моделька, используется для сохранения данных в Room

@Parcelize
@Entity
data class GoodsCart(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo val idAPI: Int,
    @ColumnInfo val title: String?,
    @ColumnInfo val price: Double?,
    @ColumnInfo val discountPercentage: Double?,
    @ColumnInfo val images: String,
) : Parcelable
