package com.example.dummyapp.ui.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.retrofit.AuthApi
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

class AuthViewModel : ViewModel(){

    private val LOG_TAG : String = AuthViewModel::class.java.simpleName

    private val authApi = RetrofitInstance.create(AuthApi::class.java)
    private val _error = MutableLiveData<String>(null)
    val error : LiveData<String>
        get() = _error

    private val _isAuth = MutableLiveData<Boolean>(false)
    val isAuth : LiveData<Boolean>
        get() = _isAuth

    val token = MutableLiveData<String>("")
    val id = MutableLiveData<Int>()


    fun auth(authRequest: AuthRequest)
    {
        try{
            //if(isInternetAvailable(this))
            //{
            CoroutineScope(Dispatchers.IO).launch {
                val response = authApi.auth(authRequest)
                lateinit var message : String
                if(response.isSuccessful)
                {
                        val user = response.body()
                        if(user != null)
                        {
                            token.postValue(user.token)
                            id.postValue(user.id)
                            _isAuth.postValue(true)
//                            token.value = user.token
//                            id.value = user.id
//                            _isAuth.value = true

                    }
                }else
                {
                    val errorBody : ResponseBody? = response.errorBody()
                    message = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    }.toString()
                    if(response.code() in 400..499 )
                    {
                        _error.value = "${response.code()}: ${errorBody?.string()}"
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }
                    else if(response.code() >= 500)
                    {
                        _error.value = "${response.code()}: ${errorBody?.string()}"
                        //runOnUiThread { binding.error.text= message}
                        Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                    }

                }

            }
//            }
//            else{
//                throw IOException("No internet connection")
//            }

        }catch(e : IOException)
        {
            _error.value = "${e.message}"
            Log.d(LOG_TAG, "Error: " + e.message)
        }

    }

}