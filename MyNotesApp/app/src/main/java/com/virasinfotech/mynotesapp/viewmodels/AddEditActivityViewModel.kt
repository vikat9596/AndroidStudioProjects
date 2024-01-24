package com.virasinfotech.mynotesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.virasinfotech.mynotesapp.database.Database
import com.virasinfotech.mynotesapp.database.Note


class AddEditActivityViewModel(private var application : Application): AndroidViewModel(application) {

    fun insert(note : Note){

           Database.getDb(application).Dao().insert(note)

    }

    fun update(note : Note){

        Database.getDb(application).Dao().update(note)

    }

    fun delete(note : Note){

        Database.getDb(application).Dao().delete(note)

    }



}