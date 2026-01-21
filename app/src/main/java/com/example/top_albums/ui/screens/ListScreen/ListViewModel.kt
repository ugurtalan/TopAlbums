package com.example.top_albums.ui.screens.ListScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top_albums.data.repository.MainRepo
import com.example.top_albums.domain.model.FilterUiState
import com.example.top_albums.domain.model.ListUiState
import com.example.top_albums.domain.model.MyAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repo: MainRepo
) : ViewModel() {
    private val albumCache = mutableMapOf<String, List<MyAlbum>>()
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()
    private val _filterState = MutableStateFlow(FilterUiState())
    val filterState: StateFlow<FilterUiState> = _filterState.asStateFlow()
    private val countriesMap = mapOf(
        "Türkiye" to "tr",
        "Argentina" to "ar",
        "Ukraine" to "ua",
        "Belgium" to "be",
        "Azerbaijan" to "az"
    )

    init {
        onSearchClicked()
    }
    fun onCountrySelected(country: String) {
        _filterState.update { it.copy(country = country) }
    }
    fun onTypeSelected(type: String) {
        val newTrait = if (type == "Music") "Most-Played" else "Top-Paid"
        val newBottomType = when (type) {
            "Music" -> "Albums"
            "Apps" -> "Apps"
            "Books" -> "Books"
            else -> ""
        }
        val newTraitOptions = if (type == "Apps" || type == "Books") {
            listOf("Top-Free", "Top-Paid")
        } else {
            listOf("Most-Played")
        }
        val newBottomOptions = if (type == "Music") {
            listOf("Albums", "Songs")
        } else {
            listOf(newBottomType)
        }

        _filterState.update {
            it.copy(
                type = type,
                trait = newTrait,
                bottomType = newBottomType,
                traitOptions = newTraitOptions,
                bottomTypeOptions = newBottomOptions
            )
        }
    }
    fun onTraitSelected(trait: String) {
        _filterState.update { it.copy(trait = trait) }
    }
    fun onBottomTypeSelected(bottomType: String) {
        _filterState.update { it.copy(bottomType = bottomType) }
    }
    fun onSearchClicked() {
        val filters = _filterState.value
        loadAlbums(filters.country, filters.type, filters.trait, filters.bottomType)
    }
    private fun loadAlbums(country: String, type: String, trait: String, bottomType: String) {
        val countryCode = countriesMap[country] ?: "tr"
        val key = "$countryCode-$type-$trait-$bottomType".lowercase()

        val cached = albumCache[key]
        if (cached != null) {
            _uiState.update { it.copy(albums = cached, isLoading = false, error = null) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repo.getAlbums(countryCode, type.lowercase(), trait.lowercase(), bottomType.lowercase())
                .onSuccess { albums ->
                    albumCache[key] = albums
                    _uiState.update { it.copy(isLoading = false, albums = albums) }
                }
                .onFailure { t ->
                    _uiState.update { it.copy(isLoading = false, error = t.message) }
                }
        }
    }
}