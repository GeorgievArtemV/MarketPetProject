package com.example.marketpetproject.di.modules.network

import com.example.marketpetproject.model.Details
import com.example.marketpetproject.model.ListGoods
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoodsAPI {
    @GET("products")
    suspend fun getGoods (@Query("limit") limit:Int,@Query("skip") skip: Int) : Response<ListGoods>
    @GET("products/{id}")
    suspend fun getDetailsGoods(@Path("id") goodsId: Int?):Response<Details>
}