package com.example.dummyapp.ui.ProductInfoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.dummyapp.LoginActivity
import com.example.dummyapp.R
import com.example.dummyapp.adapters.ViewPagerImageAdapter
import com.example.dummyapp.databinding.FragmentProductInfoBinding
import com.example.dummyapp.ui.catalog.CatalogFragmentDirections


class ProductInfoFragment : Fragment() {

    var token : String? = null

    private var _binding : FragmentProductInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : ProductInfoViewModel
    private lateinit var viewModelFactory : ProductInfoViewModelFactory

    lateinit var viewPager : ViewPager
    lateinit var viewPagerImageAdapter: ViewPagerImageAdapter
    lateinit var imageList : List<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireActivity().intent.extras
        token = args?.getString(LoginActivity.TOKEN)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        val productId = ProductInfoFragmentArgs.fromBundle(requireArguments()).productId

        viewModelFactory = ProductInfoViewModelFactory(productId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductInfoViewModel::class.java)
        token?.let { viewModel.getProduct(it, productId) }



        binding.price.text = String.format(getString(R.string.price), viewModel.product.value?.price)
        binding.titleProduct.text = viewModel.product.value?.title
        binding.rating.rating = viewModel.product.value?.rating ?: 0f
        binding.description.text = viewModel.product.value?.description

        //viewModel.product.value?.let { ViewPagerImageAdapter(it.images).also { binding.pager.adapter = it } }
//        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.product.observe(viewLifecycleOwner, Observer{
            viewPagerImageAdapter =
                viewModel.product.value?.images?.let { ViewPagerImageAdapter(requireContext(), it) }!!
            binding.pager.adapter = viewPagerImageAdapter

            binding.price.text = String.format(getString(R.string.price), viewModel.product.value?.price)
            binding.titleProduct.text = viewModel.product.value?.title
            binding.rating.rating = viewModel.product.value?.rating ?: 0f
            binding.description.text = viewModel.product.value?.description
        })







    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}