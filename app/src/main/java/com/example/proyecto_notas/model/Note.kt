package com.example.proyecto_notas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String,
    val description: String
){
    constructor(title: String, description: String) : this(0, title, description)
}