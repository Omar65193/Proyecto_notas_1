package com.example.proyecto_notas

import android.app.Application
import androidx.room.Room
import com.example.proyecto_notas.data.noteDatabase

class NoteApp : Application()
{
    val room = Room.databaseBuilder(this, noteDatabase::class.java, "note" ).build()
}