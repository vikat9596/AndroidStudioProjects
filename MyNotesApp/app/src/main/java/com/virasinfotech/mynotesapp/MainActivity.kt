package com.virasinfotech.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.virasinfotech.mynotesapp.database.Note
import com.virasinfotech.mynotesapp.databinding.ActivityMainBinding
import com.virasinfotech.mynotesapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
     lateinit var noteList : List<Note>
    lateinit var viewModel: MainActivityViewModel
    lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        noteList = ArrayList()

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        var observer = Observer<List<Note>>{
            noteList =it

            adapter = RvAdapter(noteList,this)
            binding.rv.layoutManager= LinearLayoutManager(this)
            binding.rv.adapter =adapter
        }
        viewModel.notesList.observe(this,observer)

        adapter = RvAdapter(noteList,this)
        binding.rv.layoutManager= LinearLayoutManager(this)
        binding.rv.adapter =adapter

        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this@MainActivity,AddEditActivity::class.java)
            startActivity(intent)
        }
    }
}