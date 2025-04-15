package com.example.quranapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranapp.data.model.SearchMatch
import com.example.quranapp.data.network.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchMatch>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        // Batalkan pencarian sebelumnya jika ada
        searchJob?.cancel()
        // Jangan lakukan pencarian jika query kosong
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _errorMessage.value = null
            _isLoading.value = false
            return
        }
        // Debounce: tunggu 500ms setelah user berhenti mengetik
        searchJob = viewModelScope.launch {
            delay(500L)
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.searchQuran(query)
                if (response.code == 200) {
                    _searchResults.value = response.data.matches
                } else {
                    _errorMessage.value = "Error: ${response.status} (Code: ${response.code})"
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage ?: "Unknown error"}"
                _searchResults.value = emptyList()
                // Tambahkan log jika perlu: Log.e("SearchViewModel", "Search failed", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk membersihkan state saat layar ditutup (opsional)
    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        _errorMessage.value = null
        _isLoading.value = false
        searchJob?.cancel()
    }
}
