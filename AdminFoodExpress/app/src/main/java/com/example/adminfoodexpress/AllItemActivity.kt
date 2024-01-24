package com.example.adminfoodexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodexpress.adapter.MenuItemAdapter
import com.example.adminfoodexpress.databinding.ActivityAllItemBinding
import com.example.adminfoodexpress.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()

    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retriveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }



    }

    private fun retriveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data before populating
                menuItems.clear()
                //loop for through each item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuItemAdapter(this,menuItems,databaseReference){position ->
            deleteMenuItem(position)
        }
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter=adapter
    }

    private fun deleteMenuItem(position: Int) {
        val menuItemToDelete =menuItems[position]
        val menuItemKey= menuItemToDelete.key
        val foodMenuReference= database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener { task ->
            if(task.isSuccessful){
                menuItems.removeAt(position)
                binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this, "Item not deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}