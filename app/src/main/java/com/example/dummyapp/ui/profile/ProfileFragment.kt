package com.example.dummyapp.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.R
import com.example.dummyapp.databinding.FragmentProfileBinding
import com.example.dummyapp.datastore.DataStoreManager
import com.example.dummyapp.ui.catalog.CatalogViewModel
import com.example.dummyapp.ui.catalog.CatalogViewModelFactory
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    var _binding : FragmentProfileBinding? = null
    val binding get() = _binding!!

    var token : String? = null
    var userId : Int? = null

    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var viewModelFactory : ProfileViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireActivity().intent.extras
        token = args?.getString(LoginActivity.TOKEN)
        userId = args?.getInt(LoginActivity.USER_ID)

        dataStoreManager = DataStoreManager(requireContext())







    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.logOutButton.setOnClickListener{
            showLogOutDialog(requireContext())
        }

        binding.favourites.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favouritesFragment)
        }






        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ProfileViewModelFactory(token!!, userId!!)
        profileViewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        profileViewModel.getUserById()

        profileViewModel.phone.observe(viewLifecycleOwner, Observer { _phone ->
            binding.phone.setMainText(_phone)

        })
        profileViewModel.email.observe(viewLifecycleOwner, Observer{_email ->
            binding.email.setMainText(_email)
        })
        profileViewModel.birthday.observe(viewLifecycleOwner, Observer{_birthday ->
            binding.birthday.setMainText(_birthday)
        })
        profileViewModel.sex.observe(viewLifecycleOwner, Observer{_sex ->
            binding.sex.setMainText(_sex)
        })
        profileViewModel.image.observe(viewLifecycleOwner, Observer{ _image ->
            Picasso.get().load(_image).into(binding.profileImage)
        })









    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun showLogOutDialog(context : Context){

        val builder : AlertDialog.Builder = AlertDialog.Builder(context)
        val message = resources.getString(R.string.exit_dialog_message)
        builder
            .setMessage(message)
            .setPositiveButton("Yes") { dialog, which ->
                logOut()

            }
                .setNegativeButton("No"){dialog, which ->

            }

        val dialog : AlertDialog = builder.create()
        dialog.show()

    }

    fun logOut(){
        lifecycleScope.launch {
            dataStoreManager.clear()
        }

        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}