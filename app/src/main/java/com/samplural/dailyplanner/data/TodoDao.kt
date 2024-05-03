package com.samplural.dailyplanner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)
    @Update
    suspend fun update(todo: Todo)
    @Delete
    suspend fun delete(todo: Todo)
    @Query("SELECT * from todos WHERE id = :id")
    fun getTodo(id: Int): Flow<Todo>
    @Query("SELECT * from todos ORDER BY id ASC")
    fun getAllItems(): Flow<List<Todo>>
}