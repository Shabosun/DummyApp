package com.example.dummyapp.retrofit

import com.example.dummyapp.retrofit.model.Carts
import com.example.dummyapp.retrofit.model.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface CartApi {

    @Headers("Content-Type:application/json")
    @GET("auth/carts/user/{id}")
    suspend fun getCartsByUserId(@Header("Authorization") token : String, @Path("id") id : Int) : Response<Carts>
}