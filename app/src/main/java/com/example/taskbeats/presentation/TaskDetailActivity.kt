package com.example.taskbeats.presentation

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
import com.example.taskbeats.R
import com.example.taskbeats.data.Task
import com.google.android.material.snackbar.Snackbar


class TaskDetailActivity : AppCompatActivity() {
    private var task: Task? = null
    private lateinit var bntDone: Button

    companion object{
      private const val TASK_DETAIL_EXTRA = "task.extra.detail"

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
        setSupportActionBar(findViewById(R.id.toolbar))

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
            if(title.isNotEmpty() && desc.isNotEmpty()){
                if (task == null){
                    addOrUpdateTask(0,title, desc, ActionType.CREATE)
                }else{
                    addOrUpdateTask(task!!.id, title, desc, ActionType.UPDATE)
                }
            }else{
                showMessange(it, "Fields are required")
            }
        }
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_tassk_detail, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {
                if (task != null) {
                    returnAction(task!!, ActionType.DELETE)
                }else{
                    showMessange(bntDone, "Item not found")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
    }
    }
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