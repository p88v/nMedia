package ru.netology.nmedia.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApiService {

    @GET("api/posts")
    suspend fun getAll(): List<PostDTO>

    @DELETE("api/posts/{id}")
    suspend fun deleteById(@Path("id")id: Long)

    @GET("api/posts/{id}")
    suspend fun getById(@Path("id") id: Long): PostDTO

    @DELETE("api/posts/{id}/likes")
    suspend fun disLike(@Path("id") id: Long)

    @POST("api/posts/{id}/likes")
    suspend fun likeById(@Path("id") id: Long)

    @POST("api/posts")
    suspend fun save(@Body post: PostDTO): PostDTO
}