package com.lifeandme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BirthdayEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun birthdayDao(): BirthdayDao
}