package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post
import kotlin.Long


@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val countShare: Int = 0,
    val countViews: Int = 0,
    val video: String? = null,
) {
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = published,
        likes = likes,
        likedByMe = likedByMe,
        countShare = countShare,
        countViews = countViews,
        video = video,
    )

    companion object {
        fun fromDto(dto: Post): PostEntity {
          return   PostEntity(
                id = dto.id,
                author = dto.author,
                content = dto.content,
                published = dto.published,
                likes = dto.likes,
                likedByMe = dto.likedByMe,
                countShare = dto.countShare,
                countViews = dto.countViews,
                video = dto.video,
            )
        }
    }


}
