package com.example.taskbeats.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

//4 - Data Access Objects //DAO
//fornece os métodos que o restante do app usa para interagir com os dados na tabela
@Dao
interface TaskDao {
    //5 - fun para inserir
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    //8 - lista de task
    @Query("Select * from task")
    fun getAll(): LiveData<List<Task>>

    // 12 update - encontrar tarrefa que queremos alterar
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)

    //15 - Deletando todos
    @Query("DELETE from task")
    fun deleteAll()

    //19 - Deletando por id
    @Query("DELETE from task WHERE id =:id")
    fun deleteById(id: Int)


}