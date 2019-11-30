package com.example.footballmatchschedule.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey
    var name: String,
    var value: String
)