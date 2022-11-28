package com.example.proyecto_notas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Media (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var noteID: Int,
    var path: String,
    var description:String
){
    constructor(noteID: Int,path: String, description: String) : this(0, noteID, path,description)
}