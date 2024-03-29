package com.example.dummyapp.ui.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.CartPost
import com.example.dummyapp.retrofit.model.Product
import com.example.dummyapp.retrofit.model.ProductsPost
import com.example.dummyapp.room.ProductDB
import com.example.dummyapp.room.ProductDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException


class CatalogViewModel (val token : String,val userId : Int, val dao : ProductDao) : ViewModel() {


    private  val LOG_TAG = "CatalogViewModel"






    //var token : String? = null
    //var userId : Int? = null
    private val api = RetrofitInstance.create(ProductApi::class.java)
    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>> = _products

    private val _categories = MutableLiveData<List<String>>()
    val categories : LiveData<List<String>> = _categories

    private val _navigateToProduct = MutableLiveData<Int?>()
    val navigateToProduct : LiveData<Int?>
        get() = _navigateToProduct

    private val _info_message = MutableLiveData<String?>()
    val info_message : LiveData<String?>
        get() = _info_message




    private val _error = MutableLiveData<String?>()
    val error : LiveData<String?>
        get() = _error

    fun clearError()
    {
        _error.value = null
    }

    fun addProductToFavourites(product : Product) {

        val disposable = Completable.fromAction{
            //val product = products.value?.get(productId)
            if (product != null) {
                dao.insertProduct(
                    ProductDB(
                        product.id,
                        product.title,
                        product.description,
                        product.price,
                        product.discountPercentage,
                        product.rating,
                        product.stock,
                        product.brand,
                        product.category,
                        product.thumbnail
                    )
                )
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(/* onComplete = */ {
                        _info_message.postValue("Added to favorites")
                       Log.e(LOG_TAG, "addProductToFavorites: onComplete")

            },/* onError = */ {
                Log.e(LOG_TAG,  it.message.toString())

            })




    }

    fun getProducts()
    {
        //сделать проверку на интернет

        try{
            lateinit var message : String
            viewModelScope.launch{
                val response = token?.let { api.getAllProducts(it) }
                message = response?.errorBody()?.string()?.let {
                    JSONObject(it).getString("message")
                }.toString()
                if(response?.isSuccessful == true)
                {
                    _products.value = response.body()?.products
                }
                else
                {
                    val errorBody : ResponseBody? = response?.errorBody()

                    message = response?.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    }.toString()
                    if(response?.code() in 400..499 )
                    {

                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
                    }
                    else if(response?.code()!! >= 500)
                    {
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }
                }

            }
        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }







    }

    fun addProductToCart(productId : Int)
    {
        try{
            lateinit var message : String
            viewModelScope.launch {
                    val productPost = ProductsPost(productId, 1)
                    val productPostList = listOf(productPost)
                    val cartPost = userId?.let{CartPost(it, productPostList)}

                    val response = token?.let{api.addNewCart(it, cartPost!!)}
                    if(response?.isSuccessful == true)
                    {
                        _error.value = "Product added to cart"
                    }
                    else{
                        val errorBody : ResponseBody? = response?.errorBody()
                        message = response?.errorBody()?.string()?.let {
                            JSONObject(it).getString("message")
                        }.toString()
                        if(response?.code() in 400..499 )
                        {

                            _error.value = message
                            Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
                        }
                        else if(response?.code()!! >= 500)
                        {
                            _error.value = message
                            Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                        }
                    }
                }


        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }



    }


    fun getProductsOfCategory(category : String)
    {
        try
        {
            lateinit var message : String
            viewModelScope.launch{
                val response = token?.let{api.getProductsOfCategory(it, category)}
                if(response?.isSuccessful == true)
                {
                    _products.value = response.body()?.products
                }
                else
                {
                    val errorBody : ResponseBody? = response?.errorBody()
                    message = response?.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    }.toString()
                    if(response?.code() in 400..499 )
                    {
                        //runOnUiThread { binding.error.text = message }
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
                    }
                    else if(response?.code()!! >= 500)
                    {
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }
                }
            }
        }
        catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }


    }


    fun getProductsByName(name : String)
    {
        try{
            lateinit var message : String
            viewModelScope.launch{
                val response = token?.let{api.getProductsByName(it, name)}
                if(response?.isSuccessful == true)
                {
                    _products.value = response.body()?.products
                }
                else
                {
                    val errorBody : ResponseBody? = response?.errorBody()
                    message = response?.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    }.toString()
                    if(response?.code() in 400..499 )
                    {
                        //runOnUiThread { binding.error.text = message }
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
                    }
                    else if(response?.code()!! >= 500)
                    {
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }
                }
            }
        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }

    }

    fun getAllProductCategories()
    {
        try{
            lateinit var message : String
            viewModelScope.launch{
                val response = token?.let{api.getAllProductCategories(it)}
                if(response?.isSuccessful == true)
                {
                    _categories.value = response.body()
                }
                else
                {
                    val errorBody : ResponseBody? = response?.errorBody()
                    message = response?.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    }.toString()
                    if(response?.code() in 400..499 )
                    {

                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response?.code())
                    }
                    else if(response?.code()!! >= 500)
                    {
                        _error.value = message
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }
                }
            }
        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            _error.value = e.message
        }

    }






    fun onProductClicked(productId: Int) {
        _navigateToProduct.value = productId
    }
    fun onProductNavigated() {
        _navigateToProduct.value = null
    }
}