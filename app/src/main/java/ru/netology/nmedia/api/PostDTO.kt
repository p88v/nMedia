package ru.netology.nmedia.api

import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.SyncStatus
import ru.netology.nmedia.entity.PostEntity
import kotlin.time.Instant

data class PostDTO(
    val id: Long,
    val author: String,
    val content: String,
    val published: Instant,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val authorAvatar: String? = null,
    val syncStatus: SyncStatus,
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
                authorAvatar = post.authorAvatar,
                syncStatus = post.syncStatus,
            )
        }

        fun fromEntity(post: PostEntity): PostDTO{
            return PostDTO(
                id = post.id,
                author = post.author,
                content = post.content,
                published = post.published,
                likes = post.likes,
                likedByMe = post.likedByMe,
                authorAvatar = post.authorAvatar,
                syncStatus = post.syncStatus,
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
                authorAvatar = postDTO.authorAvatar,
                syncStatus = postDTO.syncStatus,
            )
        }
    }

}