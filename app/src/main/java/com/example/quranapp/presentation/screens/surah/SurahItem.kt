package com.example.quranapp.presentation.screens.surah

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quranapp.data.model.Surah

@Composable
fun SurahItem(surah: Surah) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* TODO: navigate to detail */ },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${surah.number}. ${surah.englishName}",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = surah.name,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = surah.englishNameTranslation,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
