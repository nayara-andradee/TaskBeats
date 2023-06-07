package com.example.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar


class TaskDetailActivity : AppCompatActivity() {

    //mudei a declaracao val task para ca para poder recupera la fora do escopo
    //? = null == pode vim pode nao vim, opcional
    private var task: Task? = null
    //private lateinit var tvTitle: TextView
    private lateinit var bntDone: Button

    companion object{
      private const val TASK_DETAIL_EXTRA = "task.extra.detail"

        //fun de delete, mandando a açao para proxima tela
        fun start(context: Context, task: Task?): Intent{
                val intent = Intent(context, TaskDetailActivity::class.java)
                    .apply {
                        putExtra(TASK_DETAIL_EXTRA, task)
                    }
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        //depois de criar nosso toolbar, colocamos esse codigo para que ele possa implementar o menu no layout da detail
        setSupportActionBar(findViewById(R.id.toolbar))

        // recuperar string da tela anterior 1
        //mudei essa val porque nao estou mais recuperando uma string e sim uma tarefa
        //val title:String = intent.getStringExtra(TASK_DETAIL_EXTRA) ?: ""

        //recuperando a task 2                                      //caso nao tenha tarefa usa as acao ?
        //mudando essa val para que eu possa resgatar a task fora dp escopo, agora eu inicio ela qui
        task = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?

        //interagindo com a tela de criar nova tarefa
        val edtTilte = findViewById<EditText>(R.id.edt_task_title)
        val edtDescription = findViewById<EditText>(R.id.edt_task_description)
        bntDone = findViewById<Button>(R.id.btn_done)

        //setar os itens de uma tarrefa
        if (task !=  null){
            edtTilte.setText(task!!.title)
            edtDescription.setText(task!!.description)
        }

        //clik do botao
        bntDone.setOnClickListener{
            val title = edtTilte.text.toString()
            val desc = edtDescription.text.toString()

            //caso o campo esteja vazio
            if(title.isNotEmpty() && desc.isNotEmpty()){

                //se nao existir uma tarrefa/cria uma nova
                if (task == null){
                    addOrUpdateTask(0,title, desc, ActionType.CREATE)

                //se existir a tarrefa, pega o id, title, desc e faz o updata/atualizaçao
                }else{
                    addOrUpdateTask(task!!.id, title, desc, ActionType.UPDATE)
                }
            }else{
                showMessange(it, "Fields are required")
            }
        }

        // recuperar campo do xml
       // tvTitle = findViewById(R.id.tv_task_title_detail)

        // setar um novo texto na tela
       // tvTitle.text = task?.title ?:"Adicione uma tarrefa"

    }
    //val da nova tarrefa
    private fun addOrUpdateTask(
        id: Int,
        title: String,
        description:String,
        actionType: ActionType
    ){
        val task = Task(id, title, description)
        returnAction(task, actionType)
    }

    //ciclo de vida da activity
    //inflater da tela do menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_tassk_detail, menu)
        return true
    }
   // acao de delete,para quando clicar no botao delete
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {

                //para que o app nao delete algo que ainda  nao foi criado e de creche
                if (task != null) {
                    //chamando a fun return, para deletar
                    returnAction(task!!, ActionType.DELETE)
                }else{
                    showMessange(bntDone, "Item not found")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
    }
    }

    //funçao de retorno, chamada toda vez que no codigo precisar de algum retorno
    //ela pede uma tarrefa e uma açao
    private fun returnAction(task: Task, actionType: ActionType){
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showMessange(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}