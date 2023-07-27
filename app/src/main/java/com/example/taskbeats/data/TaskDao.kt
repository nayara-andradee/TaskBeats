package com.example.taskbeats.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    //inserir
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    //listar
    @Query("Select * from task")
    fun getAll(): LiveData<List<Task>>

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    //Deletando todos
    @Query("DELETE from task")
    fun deleteAll()

    //Deletando por id
    @Query("DELETE from task WHERE id =:id")
    fun deleteById(id: Int)


}