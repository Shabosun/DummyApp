package com.example.dummyapp.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.adapters.ProductItemAdapter
import com.example.dummyapp.databinding.FragmentCatalogBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CatalogFragment : Fragment() {

    var token : String? = null
    private var _binding : FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ProductItemAdapter
    private lateinit var viewModel : CatalogViewModel


    init{
        adapter = ProductItemAdapter { productId -> viewModel.onProductClicked(productId)  }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireActivity().intent.extras
        token = args?.getString(LoginActivity.TOKEN)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        val view = binding.root


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.getProductsByName(p0)
                }
                return true
            }
        })




        binding.productList.adapter = adapter
        binding.productList.layoutManager = GridLayoutManager(context, 2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        viewModel.token = token
        viewModel.getProducts()
        viewModel.getAllProductCategories()


        viewModel.products.observe(viewLifecycleOwner, Observer{productList ->
            productList?.let{
                adapter.submitList(productList)
            }
        })
        viewModel.navigateToProduct.observe(viewLifecycleOwner, Observer{productId ->
            productId?.let{
                val action = CatalogFragmentDirections.actionCatalogFragmentToProductInfoFragment(productId)
                this.findNavController().navigate(action)
                viewModel.onProductNavigated()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{message ->
            message?.let{
                Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
            }
        })


        //работает через раз, непонятно почему???
//        viewModel.categories.observe(viewLifecycleOwner, Observer{
//            categories ->
//            for(i in categories)
//            {
//                val chip = Chip(binding.chipGroup.context)
//                chip.isClickable = true
//                chip.isCheckable = true
//                chip.setTag("chip_$i")
//                chip.setText("$i")
//                chip.setOnClickListener{x -> x.setOnClickListener{
//
//
//                        viewModel.getProductsOfCategory(chip.text.toString())
//
//
//
//                }}
//                binding.chipGroup.addView(chip)
//
//
//            }
//
//
//
//        })




    }



    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}