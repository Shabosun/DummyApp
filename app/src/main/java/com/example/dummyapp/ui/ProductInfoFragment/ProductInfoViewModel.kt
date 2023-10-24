package com.example.dummyapp.ui.ProductInfoFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.Product
import kotlinx.coroutines.launch

class ProductInfoViewModel(private val productId : Int) : ViewModel() {

    val api = RetrofitInstance.create(ProductApi::class.java)
    val product = MutableLiveData<Product>()


    fun getProduct(token : String, productId : Int)
    {
        viewModelScope.launch {
            val response = api.getProductById(token, productId)
            product.value = response.body()


        }
    }




}