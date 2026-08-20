package ru.netology.nmedia.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.api.PostApiService
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryImpl(private val dao: PostDao,
                         private val apiService: PostApiService) : PostRepository {

    override fun getAll(): Flow<List<Post>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                entity.toDto()
            }
        }
    }

    override suspend fun loadFromServer() {
        val response = apiService.getAll()


        val posts = response

        posts.forEach { postDto ->
            dao.insert(
                PostEntity.fromDto(
                    Post(
                        id = postDto.id,
                        author = "User ${postDto.author}",
                        authorPicture = "http://10.0.2.2:9999/avatars/${postDto.authorPicture}",
                        attachment = postDto.attachment,
                        content = "${postDto.content}",
                        published = "Now",
                        likes = postDto.likes,
                        likedByMe = postDto.likedByMe
                    )
                )
            )

        }
    }

    override suspend fun like(id: Long) {
        dao.likeById(id)
    }

    override suspend fun share(id: Long) {
        dao.share(id)
    }

    override suspend fun remove(id: Long) {
        dao.removeById(id)
    }

    override suspend fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }




}