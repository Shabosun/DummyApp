package com.example.dummyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.dummyapp.R
import com.example.dummyapp.databinding.FragmentProductInfoBinding
import com.example.dummyapp.retrofit.model.Product
import com.squareup.picasso.Picasso
import java.util.Objects

class ViewPagerImageAdapter(val context : Context, private val imageList : List<String>) : PagerAdapter() {

    // as get count to return the size of the list.
    override fun getCount(): Int {
        return imageList.size
    }

    // on below line we are returning the object
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    // on below line we are initializing
    // our item and inflating our layout file
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)

        // on below line we are initializing
        // our image view with the id.
        val imageView: ImageView = itemView.findViewById<View>(R.id.slider_item_iv) as ImageView

        // on below line we are setting
        // image resource for image view.
        //imageView.setImageResource(imageList.get(position))
        Picasso.get().load(imageList.get(position)).into(imageView)

        // on the below line we are adding this
        // item view to the container.
        Objects.requireNonNull(container).addView(itemView)

        // on below line we are simply
        // returning our item view.
        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as RelativeLayout)
    }

//    inner class PagerViewHolder(view : View) : RecyclerView.ViewHolder(view)
//    {
//        private val binding = FragmentProductInfoBinding.bind(view)
//
//        fun bind(position : Int){
//            //Picasso.get().load(images.get(position)).into(binding.imageView)
//
//        }
//    }
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewPagerImageAdapter.PagerViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_product_info, parent, false)
//    return PagerViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewPagerImageAdapter.PagerViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int {
//        return images.count()
//    }
}