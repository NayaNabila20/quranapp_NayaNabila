package com.example.quranapp.data.model

data class AyahResponse(
    val code: Int,
    val status: String,
    val data: List<AyahEdition>
)

data class AyahEdition(
    val edition: Edition,
    val ayahs: List<Ayah>
)

data class Edition(
    val identifier: String,
    val language: String,
    val name: String
)

data class Ayah(
    val number: Int,
    val text: String,
    val numberInSurah: Int
)