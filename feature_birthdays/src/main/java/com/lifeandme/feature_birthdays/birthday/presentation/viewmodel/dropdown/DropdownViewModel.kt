package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.dropdown

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MonthDropdownViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MonthSelectionUiState())
    val uiState: StateFlow<MonthSelectionUiState> = _uiState.asStateFlow()
    val persianMonth = listOf(
        "همه ماه‌ها", "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )

    init {
        _uiState.update { it.copy(monthNames = persianMonth) }
        _uiState.update { it.copy(selectedMonthIndex = 0) }
    }

    /**
     * Updates the selected month index.
     * @param newIndex The new 1-based month index (1 to 12).
     */
    fun selectMonth(newIndex: Int) {
        _uiState.update { it.copy(selectedMonthIndex = newIndex) }
    }
}