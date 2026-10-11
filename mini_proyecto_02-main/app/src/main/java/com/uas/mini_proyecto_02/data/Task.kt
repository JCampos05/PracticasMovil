package com.uas.mini_proyecto_02.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)
