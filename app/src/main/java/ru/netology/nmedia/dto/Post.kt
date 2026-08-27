package ru.netology.nmedia.dto

data class Post(
   val id: Long = 0,
   val author: String = "",
   val authorAvatar: String = "",
   val attachment: Attachment? = null,
   val content: String = "",
   val published: String = "",
   val likes: Int = 0,
   val likedByMe: Boolean = false,
   val countShare: Int = 0,
   val countViews: Int = 0,
   val video: String? = null,
)
