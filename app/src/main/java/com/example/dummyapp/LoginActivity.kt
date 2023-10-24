package com.example.dummyapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dummyapp.databinding.ActivityLoginBinding
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.retrofit.AuthApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.AuthRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    companion object{
        const val TOKEN : String = "TOKEN"
        const val LOG_TAG : String = "Authorization"
    }

    private lateinit var binding : ActivityLoginBinding
    private lateinit var authApi : AuthApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authApi = RetrofitInstance.create(AuthApi::class.java)
        Toast.makeText(this, "If you click on Authorization you will get test data for entering", Toast.LENGTH_SHORT).show()

        binding.buttonLogin.setOnClickListener{
            auth(
                AuthRequest(
                    binding.login.text.toString(),
                    binding.password.text.toString()
                )
            )
            binding.login.text.clear()
            binding.password.text.clear()

        }

        //for test authorization
        binding.authTitle.setOnClickListener{
            binding.login.setText("kminchelle")
            binding.password.setText("0lelplR")


        }






    }


    private fun auth(authRequest: AuthRequest)
    {
        try{
            if(isInternetAvailable(this))
            {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = authApi.auth(authRequest)
                    lateinit var message : String
                    if(response.isSuccessful)
                    {
                        message = response.errorBody()?.string()?.let {
                            JSONObject(it).getString("message")
                        }.toString()
                        runOnUiThread {

                            //binding.error.text = message
                            val user = response.body()
                            if(user != null)
                            {
                                val token : String = user.token
                                //saveTokenToDataStore(token)
                                val intent : Intent = Intent(applicationContext, MainActivity::class.java)
                                intent.putExtra(TOKEN, token)
                                startActivity(intent)

                            }

                        }
                    }else
                    {
                        val errorBody : ResponseBody? = response.errorBody()
                        message = response.errorBody()?.string()?.let {
                            JSONObject(it).getString("message")
                        }.toString()
                        if(response.code() in 400..499 )
                        {
                            runOnUiThread { binding.error.text = message }
                            Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                        }
                        else if(response.code() >= 500)
                        {
                            runOnUiThread { binding.error.text= message}
                            Log.d(LOG_TAG, "Error:" + errorBody?.string() + " Error code: " + response.code())
                        }

                    }

                }
            }
            else{
                throw IOException("No internet connection")
            }

        }catch(e : IOException)
        {
            Log.d(LOG_TAG, "Error: " + e.message)
            binding.error.text = e.message
        }



    }
    private fun isInternetAvailable(context : Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
//    private fun saveTokenToDataStore(string : String)
//    {
//        val dataStoreManager = DataStoreManager()
//
//    }


}