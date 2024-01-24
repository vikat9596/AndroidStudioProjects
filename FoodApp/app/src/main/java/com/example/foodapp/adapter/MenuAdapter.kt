package com.example.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.DetailsActivity
import com.example.foodapp.Model.MenuItem
import com.example.foodapp.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }

            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            //intent to open details activity and pass data
            val intent =Intent(requireContext,DetailsActivity::class.java).apply {
                putExtra("MenuItemName",menuItem.foodName)
                putExtra("MenuItemPrice",menuItem.foodPrice)
                putExtra("MenuItemDescription",menuItem.foodDescription)
                putExtra("MenuItemImage",menuItem.foodImage)
                putExtra("MenuItemIngredient",menuItem.foodIngredient)
            }
            //start the detail activity
            requireContext.startActivity(intent)
        }
        //set data to recycler vie items name price image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menufoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)


            }
        }

    }

}


