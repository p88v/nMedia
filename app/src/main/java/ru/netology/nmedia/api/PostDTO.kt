package ru.netology.nmedia.api

import ru.netology.nmedia.dto.Attachment

data class PostDTO(
    val id: Long,
    val author: String,
    val authorPicture: String,
    val attachment: Attachment? = null,
    val content: String,
    val published: String,
    val likes: Int,
    val likedByMe: Boolean,
    val countShare: Int,
    val countViews: Int,
    val video: String?,
)