package com.example.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbeats.R
import com.example.taskbeats.data.local.local.Task


/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    //estado vazio
    private lateinit var cntContent: LinearLayout

    //adapter
    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::openTaskListDetail)
    }
    //viewModel, responsavel por trazer a listagem
    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(requireActivity().application)
    }

    //ciclo de vida, criaçao da view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate do fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }
    //aqui ele ja foi criada a view, e podemos recuperar as views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //iflate do estado de vazio
        cntContent = view.findViewById(R.id.ctn_content)

        //recyclerview
        val rvTask: RecyclerView = view.findViewById(R.id.rv_task_list)
        rvTask.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        listFromDataBase()
    }

    //listagem
    private fun listFromDataBase(){

        //observer
        val listObserver = Observer<List<Task>>{ listTasks ->
            if(listTasks.isEmpty()) {
                cntContent.visibility = View.VISIBLE
            }else{
                cntContent.visibility = View.GONE
            }
            adapter.submitList(listTasks)
        }
        //live data
        viewModel.taskListLiveData.observe(this, listObserver)
    }

    //açao do clik da task, vai para Taskdetail
    private fun openTaskListDetail(task: Task) {
        val intent = TaskDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment TaskListFragment.
         */
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }
}