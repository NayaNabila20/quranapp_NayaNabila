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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search // Import ikon spesifik yang digunakan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahScreen(
    viewModel: QuranViewModel = viewModel(),
    onSurahClick: (Int) -> Unit,
    onNavigateToSearch: () -> Unit // Tambahkan parameter navigasi search
) {
    val surahList by viewModel.surahList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSurahList()
    }

    // Gunakan Scaffold untuk menambahkan TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Al-Quran (Daftar Surah)") },
                actions = {
                    // Tombol aksi untuk search
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Cari Ayat")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors( // Sesuaikan warna jika perlu
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues -> // content lambda dari Scaffold

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Gunakan padding dari Scaffold
                .padding(horizontal = 16.dp) // Padding horizontal tambahan jika perlu
        ) {
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
                    LazyColumn(
                        // Padding top/bottom bisa ditambahkan di sini jika perlu,
                        // atau biarkan padding dari Box/Scaffold
                        // contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
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
    } // Akhir Scaffold content lambda
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
