package com.example.taskbeats

import android.app.ActivityManager.TaskDescription
import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//implementando o room
//2 - implementaçao da entities, cada task representa uma linha em uma tabela task no banco de dados
@Entity
data class Task(
    //3 - para cada tarrefa ter apenas 1 id unico
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
    ): Serializable

