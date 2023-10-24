package com.example.dummyapp.ui.ProductInfoFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductInfoViewModelFactory(private val productId : Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductInfoViewModel::class.java)){
            return ProductInfoViewModel(productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}