package com.example.dummyapp.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyapp.R
import com.example.dummyapp.databinding.ProductItemBinding
import com.example.dummyapp.retrofit.model.Product
import com.squareup.picasso.Picasso

class ProductItemAdapter(val clickListener : (productId : Int) -> Unit)
    : ListAdapter<Product, ProductItemAdapter.ViewHolder>(Comporator()) {

        class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
        {
            private val binding = ProductItemBinding.bind(view)

            fun bind(product : Product, clickListener : (productId : Int) -> Unit)
            {

                binding.headline.text = product.title
                binding.price.text = product.price.toString() + "$"
                Picasso.get().load(product.thumbnail).into(binding.tnumbImg)
                binding.root.setOnClickListener{clickListener(product.id)}
                //add click listener for button add cart
            }

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
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