package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): Flow<List<Post>>
    suspend fun share(id: Long)
    suspend fun like(id: Long)
    suspend fun save(post: Post)
    suspend fun remove(id: Long)
    suspend fun loadFromServer()
}