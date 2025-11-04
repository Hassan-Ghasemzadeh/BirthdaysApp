package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.add_textfield

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.birthday.BirthdayUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextFieldViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayUiState())
    val uiState: StateFlow<BirthdayUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState
                .map { state ->
                    val isNameValid = state.name.isNotBlank()
                    val isYearValid = state.jalaliYear.toIntOrNull() != null
                    val isMonthValid = state.jalaliMonth.toIntOrNull() != null
                    val isDayValid = state.jalaliDay.toIntOrNull() != null
                    val isHourValid = state.hour.toIntOrNull() != null
                    val isMinuteValid = state.minute.toIntOrNull() != null
                    return@map state.copy(
                        isSaveButtonEnabled = isNameValid && isYearValid && isMonthValid
                                && isDayValid && isHourValid && isMinuteValid,
                    )
                }
                .collect { validatedState ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isSaveButtonEnabled = validatedState.isSaveButtonEnabled,
                        )
                    }
                }
        }
    }

    fun updateField(event: AddBirthdayEvent) {
        _uiState.update { currentState ->
            when (event) {
                is AddBirthdayEvent.UpdateName -> currentState.copy(name = event.name)

                is AddBirthdayEvent.UpdateDay -> {
                    val newDay = event.day
                    if (newDay.isEmpty() || newDay.toIntOrNull() != null) {
                        currentState.copy(jalaliDay = newDay)
                    } else {
                        currentState
                    }
                }

                is AddBirthdayEvent.UpdateMonth -> {
                    val newMonth = event.month
                    if (newMonth.isEmpty() || newMonth.toIntOrNull() != null) {
                        currentState.copy(jalaliMonth = newMonth)
                    } else {
                        currentState
                    }
                }

                is AddBirthdayEvent.UpdateYear -> {
                    val newYear = event.year
                    if (newYear.isEmpty() || newYear.toIntOrNull() != null) {
                        currentState.copy(jalaliYear = newYear)
                    } else {
                        currentState
                    }
                }

                is AddBirthdayEvent.UpdateHour -> {
                    val newHour = event.hour
                    if (newHour.isEmpty() || newHour.toIntOrNull() != null) {
                        currentState.copy(hour = newHour)
                    } else {
                        currentState
                    }
                }

                is AddBirthdayEvent.UpdateMinute -> {
                    val newMinute = event.minute
                    if (newMinute.isEmpty() || newMinute.toIntOrNull() != null) {
                        currentState.copy(minute = newMinute)
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { BirthdayUiState() }
    }
}