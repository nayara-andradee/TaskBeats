package com.example.taskbeats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.taskbeats.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  bottomNaviView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val floatActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatActionButton.setOnClickListener{
            openTaskListDetail()
        }

        val taskListFragment = TaskListFragment.newInstance()
        val newsListFragment = NewsListFragment.newInstance()

        bottomNaviView.setOnItemSelectedListener{ menuItem ->
            val fragment = when(menuItem.itemId){
                R.id.task_list -> taskListFragment
                R.id.news_list -> newsListFragment
                else -> taskListFragment
            }
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, fragment) //erro aqui e so um fragment
                setReorderingAllowed(true)
            }

            true
        }

    }

    private fun openTaskListDetail() {
        val intent = TaskDetailActivity.start(this, null)
        startActivity(intent)
    }
}