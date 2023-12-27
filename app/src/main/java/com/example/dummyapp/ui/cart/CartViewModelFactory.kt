package com.example.dummyapp.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CartViewModelFactory(private val token : String, private val userId : Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java))
        {
            return CartViewModel(token, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}