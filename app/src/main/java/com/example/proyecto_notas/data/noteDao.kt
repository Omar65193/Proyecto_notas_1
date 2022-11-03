package com.example.proyecto_notas.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyecto_notas.model.Note
//import com.example.proyecto_notas.model.Note_reminders

@Dao
interface noteDao {

    @Insert
     fun insert(note:Note)

    @Query("SELECT * FROM Note ORDER BY id DESC")
     fun getAllNotes(): List<Note>

    @Query("SELECT * FROM Note WHERE id= :id")
     fun getById(id: Int) : LiveData<Note>
    /*
    @Transaction
    @Query("SELECT * FROM Note")
    suspend fun getNoteWithReminders(): LiveData<List<Note_reminders>>
     */
}