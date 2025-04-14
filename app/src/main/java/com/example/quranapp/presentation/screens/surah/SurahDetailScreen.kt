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

    fun toArabicNumber(value: Int): String {
        val arabicNumerals = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        return value.toString().mapNotNull { digitChar ->
            digitChar.toString().toIntOrNull()?.let { digit -> arabicNumerals.getOrNull(digit) }
        }.joinToString("")
    }

    val arabicEndSymbol = "\u06DD" // ARABIC END OF AYAH symbol

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        // 1. Teks Arab dengan HANYA SATU simbol ۝ sebelum nomor
        Text(
            // Format BARU: [Teks Arab] ۝[Nomor Arab]

            text = "$arabic $arabicEndSymbol${toArabicNumber(number)}", // <- Perubahan di sini
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Teks Terjemahan (tetap sama)
        Text(
            text = "$number. $translation",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Divider(modifier = Modifier.padding(top = 16.dp))
    }
}
