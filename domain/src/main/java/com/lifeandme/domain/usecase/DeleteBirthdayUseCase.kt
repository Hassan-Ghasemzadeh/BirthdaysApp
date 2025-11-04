package com.lifeandme.domain.usecase

import com.lifeandme.domain.repository.BirthdayRepository
import javax.inject.Inject

class DeleteBirthdayUseCase @Inject constructor(
    private val birthdayRepository: BirthdayRepository
) {
    suspend operator fun invoke(id: Long) = birthdayRepository.delete(id)
}