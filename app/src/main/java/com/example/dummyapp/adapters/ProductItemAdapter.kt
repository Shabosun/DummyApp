package com.example.dummyapp.adapters

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyapp.R
import com.example.dummyapp.databinding.ProductItemBinding
import com.example.dummyapp.retrofit.model.Product
import com.squareup.picasso.Picasso

class ProductItemAdapter(val clickListener : (productId : Int) -> Unit, //для просмотра детальной информации
                         val addCartListener : (productId : Int) -> Unit, //для добавления в корзину
                         val addFavoritesListener : (product : Product) -> Unit)



    : ListAdapter<Product, ProductItemAdapter.ViewHolder>(Comporator()) {



        class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
        {
            private val binding = ProductItemBinding.bind(view)

            fun bind(product : Product,
                     clickListener : (productId : Int) -> Unit,
                     addCartListener : (productId : Int) -> Unit,
                     addFavoritesListener : (product : Product) -> Unit)
            {

                binding.headline.text = product.title
                binding.price.text = product.price.toString() + " $"
                binding.description.text = product.description

                binding.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.oldPrice.text = Math.round(product.price.toInt() * product.discountPercentage).toString()+ " $"

                binding.discount.text = "-" + product.discountPercentage + "%"
                binding.rating.text = product.rating.toString()


                Picasso.get().load(product.thumbnail).into(binding.tnumbImg)
                binding.root.setOnClickListener{clickListener(product.id)}
                binding.buttonAddCart.setOnClickListener{addCartListener(product.id)}
                binding.buttonAddFavourite.setOnClickListener{
                    addFavoritesListener(product)
                    Log.d("mylog","add to fav")
                }

//                binding.buttonAddFavourite.setOnCheckedChangeListener { buttonView, checked ->
//                    if(checked){
//
//                        addFavoritesListener(product.id)
//                        Log.d("mylog", "checked")
//                    }else{
//                        Log.d("mylog", "not checked")
//
//                    }
//
//                }
            }



        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, addCartListener, addFavoritesListener)
    }

    class Comporator : DiffUtil.ItemCallback<Product>(){

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


}