package com.lifeandme.data.repository

import com.lifeandme.data.local.BirthdayDao
import com.lifeandme.data.local.BirthdayEntity
import com.lifeandme.domain.model.Birthday
import com.lifeandme.domain.repository.BirthdayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BirthdayRepositoryImpl @Inject constructor(private val dao: BirthdayDao) :
    BirthdayRepository {

    override suspend fun insertBirthday(birthday: Birthday): Long {
        val entity = BirthdayEntity(
            name = birthday.name,
            year = birthday.year,
            month = birthday.month,
            day = birthday.day,
            hour = birthday.hour,
            minute = birthday.minute
        )
        return dao.insertBirthday(entity)
    }

    override fun getAllBirthdays(): Flow<List<Birthday>> {
        return dao.getAllBirthdays().map { entities ->
            entities.map { entity ->
                Birthday(
                    id = entity.id,
                    name = entity.name,
                    year = entity.year,
                    month = entity.month,
                    day = entity.day,
                    hour = entity.hour,
                    minute = entity.minute
                )
            }
        }
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }
}