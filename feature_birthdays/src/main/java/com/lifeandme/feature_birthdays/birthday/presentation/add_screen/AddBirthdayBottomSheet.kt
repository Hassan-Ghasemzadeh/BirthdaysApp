@file:OptIn(ExperimentalMaterial3Api::class)

package com.lifeandme.feature_birthdays.birthday.presentation.add_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lifeandme.feature_birthdays.R
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.add_textfield.AddBirthdayEvent
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.add_textfield.TextFieldViewModel
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.birthday.BirthdayUiState
import com.lifeandme.feature_birthdays.birthday.presentation.viewmodel.bottomsheet.BottomSheetViewModel


@Composable
fun AddBirthdayBottomSheetScreen(
    viewModel: BottomSheetViewModel,
    onAddClicked: (BirthdayUiState) -> Unit,
    modifier: Modifier,
) {
    val showSheet by viewModel.isSheetOpen.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (showSheet) {
        BottomSheetContainer(
            sheetState = sheetState,
            onDismiss = {
                viewModel.toggleSheet()
            },
            onAddClicked = onAddClicked,
            modifier = modifier,
        )
    }
}

@Composable
private fun BottomSheetContainer(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onAddClicked: (BirthdayUiState) -> Unit,
    viewModel: TextFieldViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            viewModel.resetState()
        },
        sheetState = sheetState,
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(R.string.add_birthday),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = uiState.name,
            onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateName(it)) },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            label = {
                Text(
                    stringResource(R.string.name), textAlign = TextAlign.Center,
                )
            },
        )
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = uiState.jalaliYear,
                onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateYear(it)) },
                label = { Text(stringResource(R.string.year)) },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = uiState.jalaliMonth,
                onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateMonth(it)) },
                label = { Text(stringResource(R.string.month)) },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = uiState.jalaliDay,
                onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateDay(it)) },
                label = { Text(stringResource(R.string.day)) },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }
        Spacer(Modifier.height(8.dp))

        Row {
            OutlinedTextField(
                value = uiState.hour,
                onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateHour(it)) },
                label = { Text(stringResource(R.string.hour)) },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = uiState.minute,
                onValueChange = { viewModel.updateField(AddBirthdayEvent.UpdateMinute(it)) },
                label = { Text(stringResource(R.string.minute)) },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                onAddClicked(uiState)
                viewModel.resetState()
                onDismiss()
            },
            enabled = uiState.isSaveButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(stringResource(R.string.save_birthday))
        }

    }
}