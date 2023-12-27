package com.example.dummyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    companion object{
        const val LOG_TAG: String = "Main Activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)


//        val messaging = FirebaseMessaging.getInstance()
//        messaging.token.addOnCompleteListener{task ->
//            if(!task.isSuccessful)
//            {
//                Log.w(LOG_TAG, "Fetching FCM registration token failed", task.exception)
//                return@addOnCompleteListener
//            }
//            val token  = task.result
//            if(token != null)
//            {
//                Log.d(LOG_TAG, "Token: $token")
//            }
//            else
//            {
//                Log.d(LOG_TAG, "No registration token")
//            }
//        }
    }
}