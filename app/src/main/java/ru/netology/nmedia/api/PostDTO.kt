package ru.netology.nmedia.api

data class PostDTO(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean,
    val countShare: Int,
    val countViews: Int,
    val video: String?,
)