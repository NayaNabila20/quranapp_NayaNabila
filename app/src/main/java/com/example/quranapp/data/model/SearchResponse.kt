package com.example.quranapp.data.model

data class SearchResponse(
    val code: Int,
    val status: String,
    val data: SearchData
)

data class SearchData(
    val count: Int,              // Jumlah hasil yang ditemukan
    val matches: List<SearchMatch> // Daftar ayat yang cocok
)

// Mewakili satu item hasil pencarian (satu ayat yang cocok)
data class SearchMatch(
    val number: Int,            // Nomor global ayat dalam Al-Quran
    val text: String,           // Teks ayat tempat kata kunci ditemukan
    val edition: Edition,       // Edisi (misal: terjemahan) tempat ditemukan
    val surah: SurahReference,  // Referensi ke Surah
    val numberInSurah: Int      // Nomor ayat di dalam surah
)

// Referensi ringkas ke Surah, karena API search mungkin tidak mengembalikan semua detail Surah
data class SurahReference(
    val number: Int,
    val name: String,           // Nama Surah (Arab)
    val englishName: String,    // Nama Surah (Inggris)
    val englishNameTranslation: String // Terjemahan nama Surah
)
