package com.example.quranapp.data.repository

import com.example.quranapp.data.model.Surah
import com.example.quranapp.data.network.QuranApiService

// Pertimbangkan untuk inject QuranApiService via constructor jika belum
class QuranRepository(private val apiService: QuranApiService) {

    // Fungsi ini mungkin tidak diperlukan lagi jika getMetaData sudah mencakup surah
    // Atau biarkan jika ingin fetch surah secara terpisah
    suspend fun getSurahList(): List<Surah> {
        val response = apiService.getSurahList() // atau apiService.getMetaData().data.surahs
        return response.data
    }
}