package com.example.dummyapp.ui.catalog

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.Cart
import com.example.dummyapp.retrofit.model.CartPost
import com.example.dummyapp.retrofit.model.Product
import com.example.dummyapp.retrofit.model.Products
import com.example.dummyapp.retrofit.model.ProductsPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class CatalogViewModel : ViewModel() {


    private  val LOG_TAG = "CatalogViewModel"




    var token : String? = null
    var userId : Int? = null
    private val api = RetrofitInstance.create(ProductApi::class.java)
    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>> = _products

    private val _categories = MutableLiveData<List<String>>()
    val categories : LiveData<List<String>> = _categories

    private val _navigateToProduct = MutableLiveData<Int?>()
    val navigateToProduct : LiveData<Int?>
        get() = _navigateToProduct

    private val _error = MutableLiveData<String?>()
    val error : LiveData<String?>
        get() = _error

    fun clearError()
    {
        _error.value = null
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