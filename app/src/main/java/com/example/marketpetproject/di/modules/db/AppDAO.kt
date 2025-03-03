package com.example.marketpetproject.di.modules.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marketpetproject.model.GoodsCart

@Dao
interface AppDAO {
    @Query("Select * FROM goodscart")
    suspend fun getAll():List<GoodsCart>
    @Insert
    suspend fun insert(goodsCart: GoodsCart)
    @Delete
    suspend fun delete(goodsCart: GoodsCart)

    @Query("DELETE FROM goodscart")
    suspend fun nukeTable()
}