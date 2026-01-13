package com.example.top_albums.data.remote.api

import com.example.top_albums.data.remote.dto.AlbumsResponse
import retrofit2.http.GET

interface AlbumApi{


    @GET("tr/music/most-played/25/albums.json")
    suspend fun getTopAlbums(): AlbumsResponse
}