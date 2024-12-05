package com.example.todo_list.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM tasks WHERE id=:taskId")
    suspend fun delete(taskId: Int)

    @Query("SELECT * FROM tasks ORDER BY doneAT DESC, priority DESC")
    fun sortByPriorityLow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY doneAt ASC, priority DESC")
    fun sortByPriorityHigh(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE :searchQuery COLLATE NOCASE OR description LIKE :searchQuery COLLATE NOCASE")
    fun searchDatabase(searchQuery: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    fun getUncompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE tag = :tag")
    fun getTasksByTag(tag: String): Flow<List<Task>>

    @Query("SELECT DISTINCT tag FROM tasks")
    fun getTags(): Flow<List<String>>

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun checkTask(taskId: Int, isCompleted: Boolean)
}
