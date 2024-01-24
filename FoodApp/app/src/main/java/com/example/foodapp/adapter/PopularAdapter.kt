package com.example.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.DetailsActivity
import com.example.foodapp.databinding.PopulerItemBinding

class PopularAdapter (private val items: List<String>,private  val price:List<String>,private val image: List<Int>,private val requireContext:Context): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopulerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item =  items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item,price,images)

        holder.itemView.setOnClickListener{
            //seton click listener to open details

            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName",item)
            intent.putExtra("MenuItemImage",images)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return  items.size
    }
    class PopularViewHolder(private val binding: PopulerItemBinding) : RecyclerView.ViewHolder(binding.root){

        private  val imagesView = binding.imageView5
        fun bind(item: String,price: String, images: Int) {
            binding.foodNamePopular.text = item
            binding.pricePopular.text = price
            imagesView.setImageResource(images)
        }

    }

}