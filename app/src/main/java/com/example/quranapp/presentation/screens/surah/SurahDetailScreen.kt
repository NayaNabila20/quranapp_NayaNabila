package com.example.quranapp.presentation.screens.surah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.presentation.viewmodel.SurahDetailViewModel

@Composable
fun SurahDetailScreen(surahNumber: Int, viewModel: SurahDetailViewModel = viewModel()) {
    val arabAyahs by viewModel.arabAyahs.collectAsState()
    val translationAyahs by viewModel.translationAyahs.collectAsState()

    LaunchedEffect(surahNumber) {
        viewModel.fetchAyahs(surahNumber)
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        itemsIndexed(arabAyahs) { index, arab ->
            val translation = translationAyahs.getOrNull(index)
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = arab.text, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = translation?.text ?: "", style = MaterialTheme.typography.bodyMedium)
            }
            Divider()
        }
    }
}
