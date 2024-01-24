package com.virasinfotech.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.virasinfotech.mynotesapp.database.Note
import com.virasinfotech.mynotesapp.databinding.ActivityAddEditBinding
import com.virasinfotech.mynotesapp.viewmodels.AddEditActivityViewModel

class AddEditActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAddEditBinding.inflate(layoutInflater)
    }

    private lateinit var addEditActivityViewModel: AddEditActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addEditActivityViewModel = ViewModelProvider(this).get(AddEditActivityViewModel::class.java)

        if (intent.hasExtra("NOTE_EDIT")) {
            var note: Note = intent.getSerializableExtra("NOTE_EDIT") as Note
            binding.deleteBtn.isVisible=true
            binding.addNoteBtn.text = getString(R.string.save)
            binding.titleEdt.setText(note.title)
            binding.descriptionEdt.setText(note.desc)

            binding.addNoteBtn.setOnClickListener {
                var title = binding.titleEdt.text.toString()
                var description = binding.descriptionEdt.text.toString().trimEnd()

                note.title = title
                note.desc = description

                addEditActivityViewModel.update(note)
                finish()
            }
        } else {

            binding.addNoteBtn.setOnClickListener {
                var title = binding.titleEdt.text.toString()
                var description = binding.descriptionEdt.text.toString().trimEnd()
                if(title.isNotBlank()) {
                    var note = Note(title = title, desc = description)

                    addEditActivityViewModel.insert(note)
                    finish()
                }
            }

        }

        binding.deleteBtn.setOnClickListener {
            var note: Note = intent.getSerializableExtra("NOTE_EDIT") as Note
            var title = binding.titleEdt.text.toString()
            var description = binding.descriptionEdt.text.toString()

            note.title = title
            note.desc = description
            addEditActivityViewModel.delete(note)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }



    }
}