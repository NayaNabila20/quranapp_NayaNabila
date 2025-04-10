package com.example.quranapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.data.model.SurahResponse
import com.example.quranapp.data.model.Surah
import com.example.quranapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SurahViewModel : ViewModel() {

    private val _surahList = MutableStateFlow<List<Surah>>(emptyList())
    val surahList = _surahList.asStateFlow()

    fun fetchSurahList() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSurahList()
                _surahList.value = response.data
            } catch (e: Exception) {
                // bisa tambahkan log error
                _surahList.value = emptyList()
            }
        }
    }
}
