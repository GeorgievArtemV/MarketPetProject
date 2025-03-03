package com.example.marketpetproject.di.modules.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Реализиация Network модуля через Retrofit и Hilt. В интерфейсе GoodsAPI расписаны функции получения Json объекта.

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideClient(): OkHttpClient {
        val client = OkHttpClient()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return client.newBuilder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun providesGoodAPI(retrofit: Retrofit): GoodsAPI {
        return retrofit.create(GoodsAPI::class.java)
    }
}
