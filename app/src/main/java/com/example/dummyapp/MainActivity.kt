package com.example.dummyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dummyapp.services.now.ConnectivityObserver
import com.example.dummyapp.services.now.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object{
        const val LOG_TAG: String = "Main Activity"
        var CONNECTION : String = ""
    }
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {
            connect()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

    }

    suspend fun connect(){

        connectivityObserver.observe().collect {
            CONNECTION = it.toString()
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("inet", it.toString())
        }
    }
}