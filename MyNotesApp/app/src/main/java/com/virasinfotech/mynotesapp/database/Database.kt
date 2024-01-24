package com.virasinfotech.mynotesapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


@androidx.room.Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun Dao(): Dao


    companion object {
        public var roomDb: Database? = null
        fun getDb(context: Context): Database {
            if (roomDb == null) {
                roomDb =
                    Room.databaseBuilder(context,Database::class.java, "note_database.db").allowMainThreadQueries().build()
            }
            return roomDb!!
        }
    }
}