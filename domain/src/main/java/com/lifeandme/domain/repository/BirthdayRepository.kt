package com.lifeandme.domain.repository

import com.lifeandme.domain.model.Birthday
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {
    suspend fun insertBirthday(birthday: Birthday): Long

    fun getAllBirthdays(): Flow<List<Birthday>>

    suspend fun delete(id: Long)
}