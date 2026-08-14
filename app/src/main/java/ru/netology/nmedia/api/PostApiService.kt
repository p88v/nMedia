package ru.netology.nmedia.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApiService {

    @GET("api/posts")
    suspend fun getAll(): Response<List<PostDTO>>

    @POST("api/posts")
    suspend fun save(@Body post: PostDTO): Response<PostDTO>

    @GET("api/posts/{id}")
    suspend fun getPostById(@Path("id") postId: Long): Response<PostDTO>

    @POST("api/posts/{id}/likes")
    suspend fun likeByid(@Path("id") postId: Long): Response<PostDTO>

    @DELETE("api/posts/{id}/likes")
    suspend fun dislikeById(@Path("id") postId: Long): Response<PostDTO>

    @DELETE("api/posts/{id}")
    suspend fun deleteById(@Path("id") postId: Long): Response<PostDTO>
}