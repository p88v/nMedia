package ru.netology.nmedia.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.api.PostApiService
import ru.netology.nmedia.api.PostDTO
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.SyncStatus
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryImpl(
    private val dao: PostDao,
    private val apiService: PostApiService
) : PostRepository {

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
                        authorAvatar = "http://127.0.0.1:9999/avatars/${postDto.authorAvatar}",
                        content = "${postDto.content}",
                        published = postDto.published,
                        likes = postDto.likes,
                        likedByMe = postDto.likedByMe,
                        syncStatus = postDto.syncStatus,
                    )
                )
            )

        }
    }

    override suspend fun like(id: Long) {
        apiService.likeById(id)
        loadFromServer()
    }

    override suspend fun dislike(id: Long) {
        apiService.disLike(id)
        loadFromServer()
    }

    override suspend fun share(id: Long) {
        dao.share(id)
    }

    override suspend fun remove(id: Long) {
        apiService.deleteById(id)
        loadFromServer()
    }

    override suspend fun save(post: Post) {


        val postP = post.copy(syncStatus = SyncStatus.PENDING)
        val idBd = dao.insert(PostEntity.fromDto(postP))

        val needed = dao.getById(idBd)
        try {
            val reponse = apiService.save(PostDTO.toDto(postP))

            if (reponse.isSuccessful) {
                dao.insert(needed.copy(syncStatus = SyncStatus.SYNCED))
            }

        } catch (e: Exception) {
            dao.insert(needed.copy(syncStatus = SyncStatus.FAILED))
        }
    }

}