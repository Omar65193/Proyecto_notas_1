package com.example.proyecto_notas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto_notas.model.Note
import com.example.proyecto_notas.model.Reminder

@Database(entities = [Note::class, Reminder::class], version = 2, exportSchema = false)
abstract class noteDatabase : RoomDatabase(){
    abstract fun noteDao(): noteDao
    abstract fun reminderDAO(): reminderDao

    companion object {
        private var INSTANCE: noteDatabase? = null
        fun getDatabase(context: Context): noteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,noteDatabase::class.java, "note")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }



}