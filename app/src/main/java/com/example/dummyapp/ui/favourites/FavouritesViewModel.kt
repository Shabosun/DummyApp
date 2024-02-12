package com.example.dummyapp.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyapp.room.ProductDB
import com.example.dummyapp.room.ProductDao
import io.reactivex.rxjava3.core.Flowable

class FavouritesViewModel(val dao : ProductDao) : ViewModel() {

    //val products = dao.getAllProducts()






    private val _navigateToProduct = MutableLiveData<Int?>()
    val navigateToProduct : LiveData<Int?>
        get() = _navigateToProduct


    fun products() : Flowable<List<ProductDB>> {

        return dao.getAllProducts()
    }




//    fun addProductToCart(productId : Int)
//    {
//        try{
//            lateinit var message : String
//            viewModelScope.launch {
//                val productPost = ProductsPost(productId, 1)
//                val productPostList = listOf(productPost)
//                val cartPost = userId?.let{ CartPost(it, productPostList) }
//
//                val response = token?.let{api.addNewCart(it, cartPost!!)}
//                if(response?.isSuccessful == true)
//                {
//                    _error.value = "Product added to cart"
//                }
//                else{
//                    val errorBody : ResponseBody? = response?.errorBody()
//                    message = response?.errorBody()?.string()?.let {
//                        JSONObject(it).getString("message")
//                    }.toString()
//                    if(response?.code() in 400..499 )
//                    {
//
//                        _error.value = message
//                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
//                    }
//                    else if(response?.code()!! >= 500)
//                    {
//                        _error.value = message
//                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
//                    }
//                }
//            }
//
//
//        }catch(e : IOException)
//        {
//            Log.d(LOG_TAG, "Error: " + e.message)
//            _error.value = e.message
//        }
//
//
//
//    }

    fun onProductClicked(productId: Int) {
        _navigateToProduct.value = productId
    }
    fun onProductNavigated() {
        _navigateToProduct.value = null
    }

}