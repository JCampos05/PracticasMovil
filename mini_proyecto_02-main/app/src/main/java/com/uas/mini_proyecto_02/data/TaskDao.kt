package com.uas.mini_proyecto_02.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Task>>

    @Insert
    suspend fun insert(task: Task)

    @Update // editar y marcar como completada
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE isCompleted = 1")
    suspend fun deleteCompleted()
}