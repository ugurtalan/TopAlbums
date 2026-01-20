package com.example.top_albums.domain.model

data class AlbumUi(
    val isLoading: Boolean = false,
    val albums: List<MyAlbum> = listOf(),
    val error: String? = null
)

data class Choices(
    val country : String = "Türkiye",
    val type : String = "Music",
    val trait : String = "Most-Played",
    val bottomType: String = "Albums"
)


