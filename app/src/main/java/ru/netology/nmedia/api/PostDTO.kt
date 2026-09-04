package ru.netology.nmedia.api

import ru.netology.nmedia.dto.Post
import kotlin.time.Instant

data class PostDTO(
    val id: Long,
    val author: String,
    val content: String,
    val published: Instant,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val authorAvatar: String? = null
) {



    companion object{

        fun toDto(post: Post): PostDTO{
            return PostDTO(
                id = post.id,
                author = post.author,
                content = post.content,
                published = post.published,
                likedByMe = post.likedByMe,
                likes = post.likes,
                authorAvatar = post.authorAvatar
            )
        }

        fun fromDto(postDTO: PostDTO): Post {
            return Post(
                id = postDTO.id,
                author = postDTO.author,
                content = postDTO.content,
                published = postDTO.published,
                likes = postDTO.likes,
                likedByMe = postDTO.likedByMe,
                authorAvatar = postDTO.authorAvatar
            )
        }
    }

}