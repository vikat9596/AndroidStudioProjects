package com.virasinfotech.mynotesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.virasinfotech.mynotesapp.database.Database
import com.virasinfotech.mynotesapp.database.Note

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var notesList : LiveData<List<Note>>

    init {
        notesList = Database.getDb(application).Dao().getAllNotes()
    }
}