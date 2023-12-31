package com.example.dummyapp.retrofit

import com.example.dummyapp.retrofit.model.CartPost
import com.example.dummyapp.retrofit.model.Carts
import com.example.dummyapp.retrofit.model.Product
import com.example.dummyapp.retrofit.model.Products
import com.example.dummyapp.retrofit.model.UserId
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @Headers("Content-Type:application/json")
    @GET("auth/products")
    suspend fun getAllProducts(@Header("Authorization") token : String) : Response<Products>

    @Headers("Content-Type:application/json")
    @GET("auth/products/{id}")
    suspend fun getProductById(@Header("Authorization") token : String, @Path("id")id : Int) : Response<Product>

    @Headers("Content-Type:application/json")
    @GET("auth/products/search")
    suspend fun getProductsByName(@Header("Authorization") token : String, @Query("q") name : String) : Response<Products>

    @Headers("Content-Type:application/json")
    @GET("auth/products/category/{category}")
    suspend fun getProductsOfCategory(@Header("Authorization") token : String, @Path("category") category : String) : Response<Products>

    @Headers("Content-Type:application/json")
    @GET("auth//products/categories")
    suspend fun getAllProductCategories(@Header("Authorization") token : String) :  Response<List<String>>

    @Headers("Content-Type:application/json")
    @GET("auth/carts/user/{id}")
    suspend fun getCartsByUserId(@Header("Authorization") token : String, @Path("id") id : Int) : Response<Carts>

    @Headers("Content-Type:application/json")
    @POST("auth/carts/add")
    suspend fun addNewCart(@Header("Authorization") token : String, @Body cartPost: CartPost) : Response<Products>

    @Headers("Content-Type:application/json")
    @GET("users/{id}")
    suspend fun getUserById(@Header("Authorization") token : String, @Path("id") id : Int) : Response<UserId>




}