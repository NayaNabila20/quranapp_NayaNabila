package com.example.quranapp.data.network

import com.example.quranapp.data.model.AyahResponse
import com.example.quranapp.data.model.SearchResponse // Import model baru
import com.example.quranapp.data.model.SurahResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {

    @GET("v1/surah")
    suspend fun getSurahList(): SurahResponse

    @GET("v1/surah")
    suspend fun getAllSurah(): SurahResponse

    @GET("v1/surah/{number}/editions/quran-uthmani,id.indonesian")
    suspend fun getAyahsForSurah(
        @Path("number") surahNumber: Int
    ): AyahResponse

    @GET("v1/search/{keyword}/all/id.indonesian") // Cari {keyword} di semua surah (all) dalam edisi id.indonesian
    suspend fun searchQuran(
        @Path("keyword") keyword: String
    ): SearchResponse
}
