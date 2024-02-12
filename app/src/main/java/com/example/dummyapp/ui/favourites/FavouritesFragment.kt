package com.example.dummyapp.ui.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dummyapp.adapters.ProductItemAdapter
import com.example.dummyapp.databinding.FragmentFavouritesBinding
import com.example.dummyapp.retrofit.model.Product
import com.example.dummyapp.room.DatabaseInstance
import com.example.dummyapp.room.ProductDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavouritesFragment : Fragment() {

    companion object{
        private val TAG = FavouritesFragment::class.java.simpleName
    }

    private var _binding : FragmentFavouritesBinding? =null
    private val binding get() = _binding!!

    private lateinit var viewModel : FavouritesViewModel

    private lateinit var viewModelFactory: FavouritesViewModelFactory

    private var adapter : ProductItemAdapter

    private lateinit var dao: ProductDao

    private val disposable = CompositeDisposable()



    init{
        adapter = ProductItemAdapter( { productId -> viewModel.onProductClicked(productId) },
            //{productId -> viewModel.addProductToCart(productId)  }
            {}
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(layoutInflater)

        val view = binding.root

        binding.favList.adapter = adapter
        binding.favList.layoutManager = GridLayoutManager(context, 2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        dao = DatabaseInstance.getInstance(application).productDao


        viewModelFactory = FavouritesViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavouritesViewModel::class.java)

        viewModel.navigateToProduct.observe(viewLifecycleOwner, Observer{productId ->
            productId?.let{
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToProductInfoFragment(productId)
                this.findNavController().navigate(action)
                viewModel.onProductNavigated()
            }
        })

//        viewModel.products.observe(viewLifecycleOwner, Observer{list->
//            list?.let{
//                adapter.submitList(list as List<Product>)
//            }
//
//        })

    }

    override fun onStart() {
        super.onStart()

        disposable.add(viewModel.products()
            .map {
                val prod: MutableList<Product> = ArrayList<Product>()
                for (i in it) {
                    prod.add(i.toProductRetrofitModel())
                }
                return@map prod
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.submitList(it)
                Log.d(TAG, "Data getted is successfull ")
            },
                {
                    error -> Log.d(TAG, "Unable to get products list", error)
                }))

    }

    override fun onStop() {
        super.onStop()

        disposable.clear()
    }
}