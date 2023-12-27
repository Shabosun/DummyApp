package com.example.dummyapp.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.Cart
import com.example.dummyapp.retrofit.model.Product
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException

//передать сюда токен и юзер айди
class CartViewModel(val token : String, val userId: Int) : ViewModel(){

    val LOG_TAG : String = "CartViewModel"

    //var token : String? = null
    private val api = RetrofitInstance.create(ProductApi::class.java)

    private val _products = MutableLiveData<List<Product>>()
    val  products : LiveData<List<Product>> get() = _products

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error





    //fun getCartsByUserId(userId : Int)
    fun getCartsByUserId()
    {
        try
        {
            lateinit var message : String
            viewModelScope.launch {
                val response = token?.let { api.getCartsByUserId(it, userId) }
                message = response?.errorBody()?.string()?.let {
                    JSONObject(it).getString("message")
                }.toString()

                if(response?.isSuccessful == true)
                {
                    //_products.value = response.body()?.carts?.get(0)?.products
                    _products.value = response.body()?.carts?.let { createProductList(it) }
                }
                else
                {
                    _error.value = message + " code: " + response?.code()
                }

            }

        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }

    }

    private fun createProductList(carts : List<Cart>) : List<Product>
    {
        val productList : MutableList<Product> = mutableListOf<Product>()
        for(i in carts)
        {
            for(j in i.products)
            {
                productList.add(j)
            }
        }

        return productList

    }



}