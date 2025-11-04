package com.lifeandme.domain.usecase

import com.lifeandme.domain.model.Birthday
import com.lifeandme.domain.repository.BirthdayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBirthdaysUseCase @Inject constructor(
    private val birthdayRepository: BirthdayRepository
) {
    operator fun invoke(): Flow<List<Birthday>> = birthdayRepository.getAllBirthdays()
}