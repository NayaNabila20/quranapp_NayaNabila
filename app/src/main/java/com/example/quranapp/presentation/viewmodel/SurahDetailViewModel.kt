package com.example.quranapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.data.model.Ayah
import com.example.quranapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SurahDetailViewModel : ViewModel() {

    private val _arabAyahs = MutableStateFlow<List<Ayah>>(emptyList())
    val arabAyahs = _arabAyahs.asStateFlow()

    private val _translationAyahs = MutableStateFlow<List<Ayah>>(emptyList())
    val translationAyahs = _translationAyahs.asStateFlow()

    fun fetchAyahs(surahNumber: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAyahsForSurah(surahNumber)
                val arab = response.data.find { it.edition.identifier == "quran-uthmani" }
                val ind = response.data.find { it.edition.identifier == "id.indonesian" }
                _arabAyahs.value = arab?.ayahs ?: emptyList()
                _translationAyahs.value = ind?.ayahs ?: emptyList()
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
