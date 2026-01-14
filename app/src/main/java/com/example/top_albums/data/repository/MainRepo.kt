package com.example.top_albums.data.repository

import com.example.top_albums.data.remote.api.AlbumApi
import com.example.top_albums.domain.model.MyAlbum
import javax.inject.Inject

class MainRepo @Inject constructor (
    private val api: AlbumApi
)
{
    suspend fun getAlbums() : Result<List<MyAlbum>>{
        return try {
            val response = api.getTopAlbums()
            val albums = response.feed.results.map { dto ->
                MyAlbum(
                    id = dto.id,
                    name = dto.name,
                    artist = dto.artistName,
                    imageUrl = dto.artworkUrl100,
                    appleUrl = dto.url
                )
            }
            Result.success(albums)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}