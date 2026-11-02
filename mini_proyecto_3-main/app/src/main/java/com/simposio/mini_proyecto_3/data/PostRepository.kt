package com.simposio.mini_proyecto_3.data

import kotlinx.coroutines.flow.Flow

class PostRepository(private val dao: PostDao) {
    val posts: Flow<List<Post>> = dao.getAll()

    suspend fun save(post: Post) {
        dao.insert(post)
    }

    suspend fun update(post: Post) {
        dao.update(post)
    }

    suspend fun delete(post: Post) {
        dao.delete(post)
    }
}