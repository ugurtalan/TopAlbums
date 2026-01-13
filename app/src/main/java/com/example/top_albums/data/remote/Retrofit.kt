package com.example.retrofittrial.data.remote

import com.example.top_albums.data.remote.api.AlbumApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val BASE_URL = "https://rss.marketingtools.apple.com/api/v2/"


    val api : AlbumApi by lazy{
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(
            AlbumApi::class.java)
    }
}