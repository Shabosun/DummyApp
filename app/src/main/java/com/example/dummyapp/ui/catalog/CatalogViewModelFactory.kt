package com.example.dummyapp.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyapp.room.ProductDao
import dagger.assisted.AssistedFactory


class CatalogViewModelFactory( private val token : String, private val userId : Int, private val dao : ProductDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CatalogViewModel::class.java))
        {
            return CatalogViewModel(token, userId, dao) as T
        }
        throw IllegalArgumentException("Unknown error")
    }
}