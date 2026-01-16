package com.example.top_albums.data.remote.api

import com.example.top_albums.data.remote.dto.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumApi{


    @GET("{country}/{type}/{trait}/25/{bottomType}.json")
    suspend fun getTopAlbums(
        @Path("country") country:String,
        @Path("type")  type:String,
        @Path("trait") trait:String,
        @Path("bottomType") bottomType:String
    ): Response
}