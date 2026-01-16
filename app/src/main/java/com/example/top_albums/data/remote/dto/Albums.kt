package com.example.top_albums.data.remote.dto

data class Response(
    val feed: Feed
)

data class Feed(
    val title: String,
    val results: List<Type>
)






data class Type (
    val artistName: String,
    val id: String,
    val name: String,
    val releaseDate: String,
    val kind: String,
    val artistId: String,
    val artistUrl: String,
    val artworkUrl100: String,
    val genres: List<Genre>,
    val url: String,
    val contentAdvisoryRating: String?
)


data class Genre(
    val genreId: String,
    val name: String,
    val url: String
)

















































