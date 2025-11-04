package com.lifeandme.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {
    @Insert
    suspend fun insertBirthday(birthday: BirthdayEntity): Long

    @Query("SELECT * FROM birthdays ORDER BY month,day")
    fun getAllBirthdays(): Flow<List<BirthdayEntity>>

    @Query("DELETE FROM birthdays WHERE id = :id")
    suspend fun delete(id: Long)
}