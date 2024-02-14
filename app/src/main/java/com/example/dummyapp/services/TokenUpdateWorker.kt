package com.example.dummyapp.services

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.retrofit.AuthApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Work manager для того чтобы в фоне обновлять токен
class TokenUpdateWorker(context : Context, params : WorkerParameters) : Worker(context, params) {

    companion object{
        val  LOG_TAG = TokenUpdateWorker::class.java.simpleName.toString()
    }

    private val api = RetrofitInstance.create(AuthApi::class.java)
    private val dataStoreManager : DataStoreManager = DataStoreManager(context)




    override fun doWork(): Result {
        try{
            updateToken()


            Log.d(LOG_TAG, "doWork")
        }   catch(ex : Exception){

            Log.d(LOG_TAG, "error:${ex.message}")
            return Result.failure()
        }

        return Result.success()
    }


    fun updateToken() {
        CoroutineScope(Dispatchers.IO).launch {
            //из дата сторе берем логин и пароль
            val login = dataStoreManager.read("login")
            val password = dataStoreManager.read("password")
            if (login != null && password != null) {
                //делаем запрос к серверу
                val response = api.auth(
                    AuthRequest(
                        login,
                        password
                    )
                )

                if (response.isSuccessful) {
                    val user = response.body()
                    val token = user?.token
                    if (token != null) {
                        //обновляем токен
                        dataStoreManager.save("token", token)

                    }

                }
            }
        }
    }

}