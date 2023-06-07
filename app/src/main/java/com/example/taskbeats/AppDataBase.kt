package com.example.taskbeats

import androidx.room.Database
import androidx.room.RoomDatabase

//6 - tabela/base de dados *AppDataBase
//a configuração do banco de dados e serve como o ponto de acesso principal do app aos dados persistidos
@Database(entities = [Task::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
}