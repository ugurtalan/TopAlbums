package com.example.top_albums.domain.model

data class AlbumUi(
    val isLoading: Boolean = false,
    val albums: List<MyAlbum> = listOf(),
    val error: String? = null
)

data class Choices(
    val country : String = "tr",
    val type : String = "music",
    val trait : String = "most-played",
    val bottomType: String = "albums"
)
