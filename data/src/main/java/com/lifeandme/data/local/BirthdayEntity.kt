package com.lifeandme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthdays")
data class BirthdayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val year: Int,//gregorian
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
)
