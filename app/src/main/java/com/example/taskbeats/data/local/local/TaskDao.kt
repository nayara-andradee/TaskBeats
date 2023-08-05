package com.example.taskbeats.data.local.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskbeats.data.local.local.Task

@Dao
interface TaskDao {
    //inserir
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    //listar
    @Query("Select * from task")
    fun getAll(): LiveData<List<Task>>

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: Task)

    //deletando todos
    @Query("DELETE from task")
    suspend fun deleteAll()

    //Deletando por id
    @Query("DELETE from task WHERE id =:id")
    suspend fun deleteById(id: Int)


}