package com.example.dummyapp.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.MainActivity
import com.example.dummyapp.R
import com.example.dummyapp.databinding.FragmentAuthBinding
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.retrofit.model.AuthRequest
import kotlinx.coroutines.launch
import kotlin.math.log

class AuthFragment : Fragment() {

    private var _binding : FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataStoreManager : DataStoreManager
    private lateinit var viewModel: AuthViewModel

    companion object {
        fun newInstance() = AuthFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


        binding.buttonLogin.setOnClickListener {
            viewModel.auth(
                AuthRequest(
                    binding.login.text.toString(),
                    binding.password.text.toString()
                )
            )
        }

        //for test authorization
        binding.authTitle.setOnClickListener{
            binding.login.setText("kmeus4")
            binding.password.setText("aUTdmmmbH")

        }

        viewModel.isAuth.observe(viewLifecycleOwner, Observer{ result ->
            if(result)
            {
                lifecycleScope.launch {
                    dataStoreManager.save("token", viewModel.token.value!!) //если isAuth не null, то и token не может быть null
                    Log.d("mylog_is_auth", "${id}")
                    dataStoreManager.save("id", viewModel.id.value!!.toString())

                    Log.d("auth log", viewModel.id.value.toString())
                    Log.d("auth log", viewModel.token.value.toString())


                }

                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra(LoginActivity.TOKEN, viewModel.token.value)
                intent.putExtra(LoginActivity.USER_ID, viewModel.id.value)
                startActivity(intent)


            }

        })

        viewModel.error.observe(viewLifecycleOwner, Observer{
            binding.error.text = viewModel.error.value
        })




    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}