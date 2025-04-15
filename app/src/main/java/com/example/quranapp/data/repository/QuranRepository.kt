package com.example.quranapp.data.repository

import com.example.quranapp.data.model.Surah
import com.example.quranapp.data.network.QuranApiService

class QuranRepository(private val apiService: QuranApiService) {
    suspend fun getSurahList(): List<Surah> {
        val response = apiService.getAllSurah()
        return response.data // langsung kembalikan data dari response
    }
}
