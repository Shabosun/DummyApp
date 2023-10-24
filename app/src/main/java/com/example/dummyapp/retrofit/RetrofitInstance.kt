package com.example.dummyapp.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://dummyjson.com/"

    private val interceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun<T> create(service : Class<T>) : T{
        return retrofit.create(service)
    }






}