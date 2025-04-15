package com.example.quranapp.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranapp.data.model.SearchMatch
import com.example.quranapp.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onNavigateBack: () -> Unit,
) {
    val query by viewModel.searchQuery.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Bersihkan state saat layar ditutup
    DisposableEffect(Unit) {
        onDispose {
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cari Ayat (Terjemahan)") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChanged(it) },
                label = { Text("Masukkan kata kunci...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onQueryChanged("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Hapus query")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "Terjadi kesalahan",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center).padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    query.isNotBlank() && results.isEmpty() && !isLoading -> {
                        Text(
                            text = "Tidak ada hasil ditemukan untuk \"$query\"",
                            modifier = Modifier.align(Alignment.Center).padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    results.isNotEmpty() -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(results) { match ->
                                SearchResultItem(
                                    match = match
                                    // Uncomment jika ingin handle klik
                                    // onClick = { onResultClick(match.surah.number, match.numberInSurah) }
                                )
                            }
                        }
                    }
                    // Tampilkan pesan awal jika query kosong
                    query.isBlank() && !isLoading -> {
                        Text(
                            text = "Silakan masukkan kata kunci untuk memulai pencarian.",
                            modifier = Modifier.align(Alignment.Center).padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    match: SearchMatch,
    // onClick: () -> Unit // Uncomment jika ingin handle klik
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
        // .clickable { onClick() } // Uncomment jika ingin handle klik
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${match.surah.englishName} (${match.surah.name}) : ${match.numberInSurah}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = match.text, // Teks terjemahan yang mengandung keyword
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ditemukan di: ${match.edition.name}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
