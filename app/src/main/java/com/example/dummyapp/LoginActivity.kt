package com.example.dummyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dummyapp.databinding.ActivityLoginBinding
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.retrofit.AuthApi
import com.example.dummyapp.retrofit.RetrofitInstance
import com.example.dummyapp.retrofit.model.AuthRequest
import com.example.dummyapp.services.now.ConnectivityObserver
import com.example.dummyapp.services.now.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object{
        const val TOKEN : String = "TOKEN"
        const val USER_ID : String = "USER_ID"
        const val LOG_TAG : String = "Authorization"
    }

    private lateinit var binding : ActivityLoginBinding
    private lateinit var authApi : AuthApi
    private val dataStoreManager: DataStoreManager = DataStoreManager(this)
    private lateinit var connectivityObserver: ConnectivityObserver

   // private var dao : UserDao? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}