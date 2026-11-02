package com.simposio.mini_proyecto_3.presentation

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simposio.mini_proyecto_3.data.Post
import com.simposio.mini_proyecto_3.data.PostRepository
import com.simposio.mini_proyecto_3.domain.AppDatabase
import com.simposio.mini_proyecto_3.util.PhotoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = PostRepository(
        AppDatabase.getDatabase(app).postDao()
    )

    val posts: StateFlow<List<Post>> = repository.posts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun save(title: String, description: String, photoUri: String?, date: Long) {
        viewModelScope.launch {
            repository.save(
                Post(
                    title = title,
                    description = description,
                    photoUri = photoUri,
                    date = date
                )
            )
        }
    }

    fun update(post: Post) {
        viewModelScope.launch { repository.update(post) }
    }

    fun delete(post: Post) {
        viewModelScope.launch {
            repository.delete(post)
            post.photoUri?.let {
                withContext(Dispatchers.IO) { PhotoUtils.delete(getApplication(), it.toUri()) }
            }
        }
    }
}