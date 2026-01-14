package com.example.top_albums.domain.model

data class AlbumUi(
    val isLoading: Boolean = false,
    val albums: List<MyAlbum> = listOf(),
    val error: String? = null
)
