package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.dropdown


data class MonthSelectionUiState(
    val selectedMonthIndex: Int = 1,
    val monthNames: List<String> = emptyList()
)