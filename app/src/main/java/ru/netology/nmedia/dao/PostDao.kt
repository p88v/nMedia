package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.entity.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    suspend fun updById(id: Long, content: String)

    suspend fun save(postEntity: PostEntity) {
        if (postEntity.id == 0L) {
            insert(postEntity)
        } else {
            updById(postEntity.id, postEntity.content)
        }
    }

    @Query(
        """
        UPDATE posts SET
            likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
            likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    suspend fun likeById(id: Long)

    @Query("UPDATE posts SET countShare = countShare + 1 WHERE id = :id")
    suspend fun share(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun removeById(id: Long)
}