package com.virasinfotech.mynotesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virasinfotech.mynotesapp.database.Note
import com.virasinfotech.mynotesapp.databinding.RvItemBinding

class RvAdapter(var noteList: List<Note>, var context : Context) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {



    inner class MyViewHolder(var binding: RvItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = RvItemBinding.inflate(LayoutInflater.from(context), parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titleNotes.text = noteList.get(position).title
        holder.binding.descNotes.text = noteList.get(position).desc

        holder.itemView.setOnClickListener {
            var intent = Intent(context,AddEditActivity::class.java)
            intent.putExtra("NOTE_EDIT", noteList.get(position))
            context.startActivity(intent)
        }

    }
}


