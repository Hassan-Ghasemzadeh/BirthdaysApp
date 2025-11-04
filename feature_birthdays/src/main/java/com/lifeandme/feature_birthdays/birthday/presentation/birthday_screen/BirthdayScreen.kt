package com.lifeandme.feature_birthdays.birthday.presentation.birthday_screen


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifeandme.feature_birthdays.R
import com.lifeandme.feature_birthdays.birthday.presentation.add_screen.AddBirthdayBottomSheetScreen
import com.lifeandme.feature_birthdays.birthday.presentation.components.BirthdaysListView
import com.lifeandme.feature_birthdays.birthday.presentation.components.EmptyBirthdaysMessage
import com.lifeandme.feature_birthdays.birthday.presentation.components.MonthSelectionDropdown
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.birthday.BirthdayViewModel
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.bottomsheet.BottomSheetViewModel
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.dropdown.MonthDropdownViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayScreen(
    birthdayViewModel: BirthdayViewModel = hiltViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(),
    dropdownViewModel: MonthDropdownViewModel = hiltViewModel(),
) {
    val birthdays by birthdayViewModel.birthdays.collectAsState()
    val dropdownUiState by dropdownViewModel.uiState.collectAsStateWithLifecycle()
    val selectedMonthIndex = dropdownUiState.selectedMonthIndex
    LaunchedEffect(selectedMonthIndex) {
        birthdayViewModel.selectMonth(dropdownViewModel.uiState.value.selectedMonthIndex)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    bottomSheetViewModel.toggleSheet()
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add New Item"
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Spacer(modifier = Modifier.width(120.dp))
                },
                actions = {
                    MonthSelectionDropdown(dropdownViewModel)
                },
                title = {
                    Text(
                        stringResource(R.string.birthday_history),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    ) { innerPadding ->
        AddBirthdayBottomSheetScreen(
            bottomSheetViewModel,
            onAddClicked = { uiState ->
                birthdayViewModel.addBirthdayFromJalali(uiState)
            },
            Modifier.padding(innerPadding)
        )
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (birthdays.isNotEmpty()) {
                BirthdaysListView(birthdays, { id -> birthdayViewModel.delete(id) })
            } else {
                EmptyBirthdaysMessage()
            }
        }
    }
}
