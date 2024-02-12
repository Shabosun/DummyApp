package com.example.dummyapp.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dummyapp.room.ProductDao
import com.example.dummyapp.ui.catalog.CatalogViewModel

class FavouritesViewModelFactory(val dao : ProductDao) : ViewModelProvider.Factory {



    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavouritesViewModel::class.java))
        {
            return FavouritesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown error")
    }

}