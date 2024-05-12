package com.example.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: ToDoDatabaseHelper
    private var todoId: Int= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ToDoDatabaseHelper(this)

        todoId = intent.getIntExtra("todo_id", -1)
        if (todoId == -1) {
            finish()
            return

        }
        val note = db.getToDoByID(todoId)

        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener(){
            val newTitle =binding.updateTitleEditText.text.toString()
            val newContent =binding.updateContentEditText.text.toString()
            val updateToDo = ToDo(todoId, newTitle  , newContent )

            db.updateToDo(updateToDo)
            finish()
            Toast.makeText(this,"Changes saved",Toast.LENGTH_SHORT).show()


        }


    }
}