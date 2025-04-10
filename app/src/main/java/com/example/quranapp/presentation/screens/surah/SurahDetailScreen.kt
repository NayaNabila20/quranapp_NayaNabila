package com.example.quranapp.presentation.screens.surah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tampilkan Bismillah di tengah atas kecuali surah pertama (Al-Faatiha)
        if (surahNumber != 1 && surahNumber != 9 && arabAyahs.isNotEmpty()) {
            item {
                Text(
                    text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }

        itemsIndexed(arabAyahs) { index, arab ->
            val translation = translationAyahs.getOrNull(index)?.text.orEmpty()

            // Nomor ayat lokal mulai dari 1 (bukan ID dari API)
            val displayNumber = index + 1

            AyahItem(
                number = displayNumber,
                arabic = arab.text,
                translation = translation
            )
        }
    }
}

@Composable
fun AyahItem(number: Int, arabic: String, translation: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "$number.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = arabic,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = translation,
            style = MaterialTheme.typography.bodyMedium
        )
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}
