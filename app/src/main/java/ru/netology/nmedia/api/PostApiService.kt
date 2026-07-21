package ru.netology.nmedia.api

import retrofit2.http.GET


interface PostApiService{

    @GET("posts")
    suspend fun getAll(): retrofit2.Response<List<JsonPlaceholderPostDto>>
}