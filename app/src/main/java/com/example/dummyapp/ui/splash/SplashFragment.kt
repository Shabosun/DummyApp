package com.example.dummyapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.MainActivity
import com.example.dummyapp.R
import com.example.dummyapp.databinding.FragmentSplashBinding
import com.example.dummyapp.datastore.DataStoreManager
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    lateinit var dataStoreManager: DataStoreManager
    lateinit var binding : FragmentSplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)

        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.alpha = 0f
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction{

            lifecycleScope.launch {

                val token : String? = dataStoreManager.read("token")
                val id : Int?  = dataStoreManager.read("id")?.toInt()
                if(token.isNullOrEmpty())
                {
                    Log.d("mylog", "token : $token, id : $id")
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra(LoginActivity.TOKEN, token)
                    intent.putExtra(LoginActivity.USER_ID, id)
                    startActivity(intent)
                    activity?.finish()
                    //findNavController().navigate(R.id.action_authFragment_to_mainActivity)
                    Toast.makeText(context, "not null", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}