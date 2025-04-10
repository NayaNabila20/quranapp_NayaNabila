package com.example.quranapp.presentation.screens.surah

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.data.model.Surah
import com.example.quranapp.presentation.viewmodel.QuranViewModel

@Composable
fun SurahScreen(
    viewModel: QuranViewModel = viewModel(),
    onSurahClick: (Int) -> Unit // Untuk navigasi ke halaman detail berdasarkan nomor surah
) {
    val surahList by viewModel.surahList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSurahList()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Terjadi kesalahan",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn {
                    items(surahList) { surah ->
                        SurahItem(
                            surah = surah,
                            onClick = { onSurahClick(surah.number) } // Navigasi dengan number
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SurahItem(surah: Surah, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${surah.number}. ${surah.englishName} (${surah.name})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Jumlah Ayat: ${surah.numberOfAyahs} | Tipe: ${surah.revelationType}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
