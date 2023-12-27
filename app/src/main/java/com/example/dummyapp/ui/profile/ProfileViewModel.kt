package com.example.dummyapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyapp.retrofit.ProductApi
import com.example.dummyapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException

class ProfileViewModel(val token : String, val userId : Int) : ViewModel() {

    private val LOG_TAG = "ProfileViewModel"

    private val api = RetrofitInstance.create(ProductApi::class.java)

    private val _phone = MutableLiveData<String>()
    val phone : LiveData<String>
        get() = _phone

    private val _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    private val _birthday = MutableLiveData<String>()
    val birthday : LiveData<String>
        get() = _birthday

    private val _sex = MutableLiveData<String>()
    val sex : LiveData<String>
        get() = _sex

    private val _image = MutableLiveData<String>()
    val image : LiveData<String>
        get() = _image


    fun getUserById(){

        try{

            lateinit var message : String
            viewModelScope.launch{

                val response = api.getUserById(token, userId)

                message = response.errorBody()?.string()?.let {
                    JSONObject(it).getString("message")
                }.toString()

                if(response.isSuccessful == true)
                {
                    _phone.value = response.body()?.phone
                    _email.value = response.body()?.email
                    _birthday.value = response.body()?.birthDate
                    _sex.value = response.body()?.gender
                    _image.value = response.body()?.image

                }


            }

        }catch(exception : IOException){
            Log.d(LOG_TAG, "Unknown error")
        }
    }

}