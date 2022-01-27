package com.decagon.n26_p3_usecase.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.todo.model.TodoData

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ToDoViewHolder>() {

    val todos = emptyList<TodoData>()

    inner class ToDoViewHolder(itemLayout: View) : RecyclerView.ViewHolder(itemLayout) {
        val toDoHeader: TextView = itemLayout.findViewById(R.id.todo_description_textView)
        val toDoDescription: TextView = itemLayout.findViewById(R.id.todo_description_textView)
        val priority: TextView = itemLayout.findViewById(R.id.todo_priority_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false))
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.toDoHeader.text = todos[position].title
        holder.toDoDescription.text = todos[position].description
        holder.priority.text = todos[position].priority.toString()
    }

}