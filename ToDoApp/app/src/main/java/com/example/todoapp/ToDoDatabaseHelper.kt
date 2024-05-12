package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ToDoDatabaseHelper(context:Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME ="todoapp.db"
        private const val DATABASE_VERSION =1
        private const val TABLE_NAME ="alltodo"

        private const val COLUMN_ID ="id"
        private const val COLUMN_TITLE ="title"
        private const val COLUMN_CONTENT ="content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery ="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT , $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertToDo (todo: ToDo){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE,todo.title)
            put(COLUMN_CONTENT,todo.content)

        }

        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllToDo():List<ToDo>{
        val todoList = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT* FROM $TABLE_NAME"
        val cursor =db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val todo = ToDo(id, title, content)
            todoList.add(todo)
        }

        cursor.close()
        db.close()
        return todoList

    }

    fun updateToDo(todo: ToDo){
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,todo.title)
            put(COLUMN_CONTENT,todo.content)
        }

        val whereClause ="$COLUMN_ID= ?"
        val whereArgs = arrayOf(todo.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }


    fun getToDoByID(todoId: Int):ToDo{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $todoId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return ToDo(id,title,content)

    }

    fun deleteToDo(todoId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(todoId.toString())
        db.delete(TABLE_NAME, whereClause,whereArgs)
        db.close()
    }


}