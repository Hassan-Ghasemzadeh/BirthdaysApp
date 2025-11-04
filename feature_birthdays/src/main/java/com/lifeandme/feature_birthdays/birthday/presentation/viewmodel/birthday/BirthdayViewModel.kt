package com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.birthday

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeandme.core.util.AlarmScheduler
import com.lifeandme.core.util.JalaliConverter
import com.lifeandme.domain.model.Birthday
import com.lifeandme.domain.usecase.AddBirthdayUseCase
import com.lifeandme.domain.usecase.DeleteBirthdayUseCase
import com.lifeandme.domain.usecase.GetBirthdaysUseCase
import com.lifeandme.feature_birthdays.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val addBirthdayUseCase: AddBirthdayUseCase,
    private val getBirthdayUseCase: GetBirthdaysUseCase,
    private val deleteBirthdayUseCase: DeleteBirthdayUseCase,
) : ViewModel() {
    private val _selectedMonthIndex = MutableStateFlow(0)
    val selectedMonthIndex: StateFlow<Int> = _selectedMonthIndex.asStateFlow()

    private val persianToGregorianMap = intArrayOf(
        0,  // Index 0: All Months -> Month 0 (for no filter)
        4,  // Index 1: Farvardin -> April (Month 4)
        5,  // Index 2: Ordibehesht -> May (Month 5)
        6,  // Index 3: Khordad -> June (Month 6)
        7,  // Index 4: Tir -> July (Month 7)
        8,  // Index 5: Mordad -> August (Month 8)
        9,  // Index 6: Shahrivar -> September (Month 9)
        10, // Index 7: Mehr -> October (Month 10)
        11, // Index 8: Aban -> November (Month 11)
        12, // Index 9: Azar -> December (Month 12)
        1,  // Index 10: Dey -> January (Month 1)
        2,  // Index 11: Bahman -> February (Month 2)
        3   // Index 12: Esfand -> March (Month 3)
    )

    fun selectMonth(persianMonthIndex: Int) {
        val gregorianMonthToFilter = if (persianMonthIndex in persianToGregorianMap.indices) {
            persianToGregorianMap[persianMonthIndex]
        } else {
            0
        }

        _selectedMonthIndex.value = gregorianMonthToFilter
    }

    val birthdays: StateFlow<List<Birthday>> = getBirthdayUseCase()
        .combine(selectedMonthIndex) { allBirthdays, monthIndex ->

            val filteredBirthdays = if (monthIndex in 1..12) {
                allBirthdays.filter { it.month == monthIndex - 1 }
            } else {
                allBirthdays
            }

            filteredBirthdays.sortedWith(compareBy({ it.month }, { it.day }))
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    fun addBirthdayFromJalali(uiState: BirthdayUiState) {
        viewModelScope.launch {
            val gDate = JalaliConverter.jalaliToGregorian(
                jalaliDay = uiState.jalaliDay.toInt(),
                jalaliMonth = uiState.jalaliMonth.toInt(),
                jalaliYear = uiState.jalaliYear.toInt()
            )
            val birthday = Birthday(
                name = uiState.name,
                year = gDate.year,
                month = gDate.monthValue,
                day = gDate.dayOfMonth,
                hour = uiState.hour.toInt(),
                minute = uiState.minute.toInt(),
            )
            if (!looksLikeGregorian(uiState.jalaliYear.toInt())) {
                val id = addBirthdayUseCase(birthday)
                AlarmScheduler.scheduleBirthday(
                    context = context,
                    id = id,
                    name = birthday.name,
                    year = birthday.year,
                    month = birthday.month,
                    day = birthday.day,
                    hour = birthday.hour,
                    minute = birthday.minute,
                )
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.user_input_validation_message), Toast.LENGTH_SHORT
                )
            }

        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            deleteBirthdayUseCase(id)
            AlarmScheduler.cancelAlarm(context = context, id = id)
        }
    }

    fun looksLikeGregorian(year: Int): Boolean {
        return year in 1500..<2100
    }
}