package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(private var todo:List<ToDo>,context: Context):
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private val db:ToDoDatabaseHelper = ToDoDatabaseHelper(context)

    class ToDoViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.TitleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.ContentTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return ToDoViewHolder(view)
    }

    override fun getItemCount(): Int = todo.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = todo[position]
        holder.titleTextView.text= todo.title
        holder.contentTextView.text= todo.content

        holder.updateButton.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateToDoActivity::class.java).apply {
                putExtra("todo_id", todo.id)
            }

            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener(){
            db.deleteToDo(todo.id)
            refreshdata(db.getAllToDo())
        }

    }

    fun refreshdata(newToDo : List<ToDo>){
        todo = newToDo
        notifyDataSetChanged()
    }
}