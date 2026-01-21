package com.example.top_albums.domain.model



data class ListUiState(
    val isLoading: Boolean = false,
    val albums: List<MyAlbum> = emptyList(),
    val error: String? = null
)

data class FilterUiState(
    val country: String = "Türkiye",
    val type: String = "Music",
    val trait: String = "Most-Played",
    val bottomType: String = "Albums",
    val traitOptions: List<String> = listOf("Most-Played"),
    val bottomTypeOptions: List<String> = listOf("Albums", "Songs")
)




