package com.example.dummyapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.R
import com.example.dummyapp.adapters.CartItemAdapter
import com.example.dummyapp.adapters.ProductItemAdapter
import com.example.dummyapp.databinding.FragmentCartBinding
import com.example.dummyapp.ui.catalog.CatalogViewModel


class CartFragment : Fragment() {

    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    var token : String? = null
    var userId : Int? = null

    private lateinit var viewModel : CartViewModel
    private var adapter : CartItemAdapter

    init{
        adapter = CartItemAdapter()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireActivity().intent.extras
        token = args?.getString(LoginActivity.TOKEN)
        userId = args?.getInt(LoginActivity.USER_ID)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rcv.adapter = adapter
        binding.rcv.layoutManager = LinearLayoutManager(context)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        userId?.let { viewModel.getCartsByUserId(it) }
        viewModel.token = token


        viewModel.products.observe(viewLifecycleOwner, Observer{ list ->
            adapter.submitList(list)

        })

        viewModel.error.observe(viewLifecycleOwner, Observer{message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })




    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}