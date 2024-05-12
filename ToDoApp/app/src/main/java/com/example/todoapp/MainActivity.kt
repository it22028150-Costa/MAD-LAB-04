package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityAddToDoBinding
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private  lateinit var db : ToDoDatabaseHelper
    private lateinit var  todoAdapter: ToDoDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ToDoDatabaseHelper(this)
        todoAdapter = ToDoDatabaseHelper(db.getAllToDo(),this)

        binding.ToDoRecycleView.layoutManager = LinearLayoutManager(this)
        binding.ToDoRecycleView.adapter = ToDoDatabaseHelper

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddToDoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        ToDoDatabaseHelper.refreshdata(db.getAllToDo())
    }


}
