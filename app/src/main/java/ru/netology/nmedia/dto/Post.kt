package ru.netology.nmedia.dto

import kotlin.time.Clock
import kotlin.time.Instant

data class Post(
   val id: Long = 0,
   val author: String = "",
   val authorAvatar: String? = "",
   val attachment: Attachment? = null,
   val content: String = "",
   val published: Instant = Clock.System.now(),
   val likes: Int = 0,
   val likedByMe: Boolean = false,
   val countShare: Int = 0,
   val countViews: Int = 0,
   val video: String? = null,
)
