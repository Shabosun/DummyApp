package com.example.dummyapp.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyapp.R
import com.example.dummyapp.databinding.CartItemBinding
import com.example.dummyapp.databinding.ProductItemBinding
import com.example.dummyapp.retrofit.model.Cart
import com.example.dummyapp.retrofit.model.Product
import com.squareup.picasso.Picasso

class CartItemAdapter : ListAdapter<Product, CartItemAdapter.ViewHolder>(Comporator()){



    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        private val binding = CartItemBinding.bind(view)

        fun bind(product: Product)
        {
            binding.titleProductName.text = product.title
            binding.price.text = product.price.toString()+ " $"

            binding.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.oldPrice.text = Math.round(product.price.toInt() * product.discountPercentage).toString()+ " $"

            Picasso.get().load(product.thumbnail).into(binding.thumbImg)

            var cnt = 0;

            binding.buttonDecrease.setOnClickListener{
                cnt--
                binding.count.text = cnt.toString()
            }

            binding.buttonIncrease.setOnClickListener{
                cnt++
                binding.count.text = cnt.toString()
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        return CartItemAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
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