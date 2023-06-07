package com.example.taskbeats.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.taskbeats.R
import com.example.taskbeats.data.AppDataBase
import com.example.taskbeats.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var cntContent: LinearLayout

    //adapter
    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::onListItemClicked)
    }

    //ROOM
    //7 - implementando o dao, base de dados
    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "taskbeats-database"
        ).build()
    }

    private val dao by lazy {
        dataBase.taskDao()
    }

    // abrir a tela pedindo o resultado da ** acao que foi de deletar o item
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            //pegando resultado
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            //A coisa mais linda de se ve
            when (taskAction.actionType) {
                //tratando a açoa de deletar
                //21-atualizando o deletar
                ActionType.DELETE.name -> deleteById(task.id)
                //tratando a açao de criar
                //11-atualizando create
                ActionType.CREATE.name -> insertIntoDataBase(task)
                //tratando o editar/atualizar
                //14-atualizando o update
                ActionType.UPDATE.name -> updateIntoDataBase(task)
            }
        }
    }
    //inflate do layot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        //17- colocar o suporte para actionBar
        setSupportActionBar(findViewById(R.id.toolbar))

        //9 - faz parte da acao 9
        listFromDataBase()
        cntContent = findViewById(R.id.ctn_content)

        //atualizar o adapter

        // primeiro passo depois de fazer a xml, recuperar a reciclerview da xml
        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)
        rvTask.adapter = adapter

        //açao do botao
        val fab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail(null)
        }

    }

    //Create
    //Read
    //Update
    //Delete

    // 10 - fun para criar
    private fun insertIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.insert(task)
            //listar/atualizar
            listFromDataBase()
        }
    }

    // 13 - fun para atualizar
    private fun updateIntoDataBase(task: Task) {
        CoroutineScope(IO).launch {
            dao.update(task)
            listFromDataBase()
        }

    }

    //18 - fun para deletar todos
    private fun deleteAll(){
        CoroutineScope(IO).launch {
            dao.deleteAll()
            listFromDataBase()
        }
    }

    //20 - fun de deletar por id
    private fun deleteById(id: Int){
        CoroutineScope(IO).launch {
            dao.deleteById(id)
            listFromDataBase()
        }
    }

    // 9 -fun que tem que ser chamada toda vez que for atualizar
    private fun listFromDataBase(){
        //usando o dao
        CoroutineScope(IO).launch {
            val myDatabaseList: List<Task> = dao.getAll()
            adapter.submitList(myDatabaseList)

        }
    }

    //fun messagem, para quando pecisar de uma msg e so chamar essa fun
    private fun showMessange(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    // fun da activity #TaskDetailActivity/ apenas de abrir a tela
    //atualizando fun para adicinar uma tarefa
    private fun onListItemClicked(task: Task){
        openTaskListDetail(task)
    }
    private fun openTaskListDetail(task: Task? = null){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)
    }

    //16 - criar menu e colocar essas duas fun dele
    //inflate do menu de deletar tudas tasks
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }
        //açao do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_task -> {
                // deletar todas as tarrefas
                deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

//criando aaco que vai ser feita
//mudei para enum== enumerador
//CRUD == CREATE(criaçao), READ(listar), UPDATA(atualizar), DELETE(deletar)
enum class ActionType : Serializable {
    DELETE,
    UPDATE,
    CREATE
}

//data class que vai segurar a tarefa
data class TaskAction(
    val task: Task,
    val actionType: String
) : Serializable

//responsavel pelo resultado
    const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"