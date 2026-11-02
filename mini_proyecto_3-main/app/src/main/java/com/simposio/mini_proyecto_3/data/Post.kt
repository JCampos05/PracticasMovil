package com.simposio.mini_proyecto_3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val photoUri: String? = null,
    val date: Long = System.currentTimeMillis()
)
