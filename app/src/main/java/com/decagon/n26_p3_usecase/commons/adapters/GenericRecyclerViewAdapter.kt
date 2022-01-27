package com.decagon.n26_p3_usecase.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.n26_p3_usecase.R

// create a generic recycler view adapter
class GenericRecyclerViewAdapter<T>(private val items: List<T>, private val layout: Int) : RecyclerView.Adapter<GenericRecyclerViewAdapter.GenericViewHolder<T>>() {

    enum class  LayoutType{
        TODO, JOKES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {

       return when(layout){
            LayoutType.TODO.ordinal -> {
                GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false), LayoutType.TODO.ordinal)
            }
           LayoutType.JOKES.ordinal -> {
             GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false), LayoutType.JOKES.ordinal)
           }
            else -> {
                GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false), LayoutType.TODO.ordinal)
            }
        }
    }

    override fun getItemCount(): Int { return items.size }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {

        when(holder.viewType){
            LayoutType.TODO.ordinal -> {
                holder.title?.text = items[position].toString()
                holder.description?.text = items[position].toString()
            }
            LayoutType.JOKES.ordinal -> { }
        }

    }

    class GenericViewHolder<T>(val view: View, val viewType:Int) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var description: TextView? = null

       init {
           when(viewType){
               LayoutType.TODO.ordinal -> {
                   title = view.findViewById(R.id.todo_title)
                   description = view.findViewById(R.id.todo_description_textView)
               }
               LayoutType.JOKES.ordinal -> { }
           }
       }
    }
}



