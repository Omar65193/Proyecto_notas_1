package com.example.proyecto_notas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity
data class Reminder (
    @PrimaryKey val id: Int,
    var noteID: Int,
    var date: Date,
    var time: Time
)