package com.lifeandme.domain.usecase

import com.lifeandme.domain.model.Birthday
import com.lifeandme.domain.repository.BirthdayRepository
import javax.inject.Inject

class AddBirthdayUseCase @Inject constructor(
    private val birthdayRepository: BirthdayRepository
) {
    suspend operator fun invoke(birthday: Birthday) = birthdayRepository.insertBirthday(birthday)
}