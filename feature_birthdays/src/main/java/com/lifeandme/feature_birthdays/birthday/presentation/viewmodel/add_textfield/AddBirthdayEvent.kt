package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.add_textfield

sealed interface AddBirthdayEvent {
    data class UpdateName(val name: String) : AddBirthdayEvent
    data class UpdateDay(val day: String) : AddBirthdayEvent
    data class UpdateMonth(val month: String) : AddBirthdayEvent
    data class UpdateYear(val year: String) : AddBirthdayEvent
    data class UpdateHour(val hour: String) : AddBirthdayEvent
    data class UpdateMinute(val minute: String) : AddBirthdayEvent
}