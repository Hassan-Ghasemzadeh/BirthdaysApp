package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.birthday

data class BirthdayUiState(
    val name: String = "",
    val jalaliYear: String = "1402",
    val jalaliMonth: String = "1",
    val jalaliDay: String = "1",
    val hour: String = "9",
    val minute: String = "0",
    val isSaveButtonEnabled: Boolean = false,
)