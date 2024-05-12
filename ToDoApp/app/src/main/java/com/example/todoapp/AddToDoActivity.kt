package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityAddToDoBinding

class AddToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToDoBinding
    private lateinit var db:ToDoDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ToDoDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val todo = ToDo(0,title,content)
            db.insertToDo(todo)
            finish()
            Toast.makeText(this,"ToDo saved",Toast.LENGTH_SHORT).show()
        }
    }
}
