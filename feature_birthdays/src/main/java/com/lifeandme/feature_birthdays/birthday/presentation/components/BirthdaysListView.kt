package com.lifeandme.feature_birthdays.birthday.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.lifeandme.domain.model.Birthday


@Composable
fun BirthdaysListView(
    birthdays: List<Birthday>,
    onClick: (id: Long) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(birthdays) { birthday ->
            BirthdayListItem(birthday, onClick)
        }
    }
}
