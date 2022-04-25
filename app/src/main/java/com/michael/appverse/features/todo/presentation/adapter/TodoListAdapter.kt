package com.michael.appverse.features.todo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.michael.appverse.R
import com.michael.appverse.features.todo.model.Priority
import com.michael.appverse.features.todo.model.TodoData
import com.michael.appverse.features.todo.presentation.viewController.ListFragmentDirections
import com.michael.appverse.features.todo.util.TodoDiffUtil

class TodoListAdapter() : RecyclerView.Adapter<TodoListAdapter.ToDoViewHolder>() {

    var todos  = emptyList<TodoData>()

    inner class ToDoViewHolder(itemLayout: View) : RecyclerView.ViewHolder(itemLayout) {
        val toDoHeader: TextView = itemLayout.findViewById(R.id.todo_title)
        val toDoDescription: TextView = itemLayout.findViewById(R.id.todo_description_textView)
        val priority: CardView = itemLayout.findViewById(R.id.todo_priority_indicator)
        val recyclerViewLayout: View = itemLayout.findViewById(R.id.todo_row_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false)
        return ToDoViewHolder(layout)
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {

        holder.toDoHeader.text = todos[position].title
        holder.toDoDescription.text = todos[position].description

        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(todos[position])
        holder.recyclerViewLayout.setOnClickListener { holder.itemView.findNavController().navigate(action) }

        when (todos[position].priority) {
            Priority.HIGH -> holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.priority.context, R.color.red_900))
            Priority.MEDIUM -> holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.priority.context, R.color.yellow_700))
            Priority.LOW -> holder.priority.setCardBackgroundColor(ContextCompat.getColor(holder.priority.context, R.color.green_500))
        }
    }

    @JvmName("setTodos1")
    fun setTodos(todos: List<TodoData>) {
        val todoDiffUtil = TodoDiffUtil(this.todos, todos)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.todos = todos
        todoDiffResult.dispatchUpdatesTo(this)
    }

}