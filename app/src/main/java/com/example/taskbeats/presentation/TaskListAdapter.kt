package com.example.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.taskbeats.R
import com.example.taskbeats.data.local.local.Task

class TaskListAdapter(
    private val openTaskDetailView:(task: Task) -> Unit
    ): ListAdapter<Task, TaskListViewHolder>(TaskListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
       val task = getItem(position)
        holder.bind(task, openTaskDetailView)
    }
    companion object : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description
        }
    }
    }
class TaskListViewHolder(
    private val view:View
    ) : RecyclerView.ViewHolder(view){

    private val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvDes = view.findViewById<TextView>(R.id.tv_task_description)

    fun bind(
        task: Task,
        openTaskDetailView:(task: Task) -> Unit
    ){
        tvTitle.text = task.title
        tvDes.text = "${task.id}- ${task.description}"

        view.setOnClickListener {
            openTaskDetailView.invoke(task)
        }
    }
}