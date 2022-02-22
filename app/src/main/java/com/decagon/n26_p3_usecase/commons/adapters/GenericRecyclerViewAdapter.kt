package com.decagon.n26_p3_usecase.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.todo.model.Priority
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.decagon.n26_p3_usecase.features.todo.presentation.viewController.ListFragmentDirections

// create a generic recycler view adapter
class GenericRecyclerViewAdapter<T>(private val items: List<T>, private val layout: Int) : RecyclerView.Adapter<GenericRecyclerViewAdapter.GenericViewHolder<T>>() {

    /*private var items: List<T> =  emptyList()
    private var layout: Int = 0

    fun <T> setData(items: List<T>, layout: Int){
        this.items = items
        this.layout = layout
    }*/


    var data: List<T> =  items


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

                holder.title?.text = (items[position] as TodoData).title
                holder.description?.text = (items[position] as TodoData).description

                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(items[position] as TodoData)
                holder.recyclerViewLayout!!.setOnClickListener {
                    holder.itemView.findNavController().navigate(action)
                }


                when ((items[position] as TodoData).priority) {
                    Priority.HIGH -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.red_900))
                    Priority.MEDIUM -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.yellow_700))
                    Priority.LOW -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.green_500))
                }
            }
            LayoutType.JOKES.ordinal -> {}
        }

    }

    class GenericViewHolder<T>(val view: View, val viewType:Int) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var description: TextView? = null
        var priority: CardView? = null
        var recyclerViewLayout: View? = null

        init {
           when(viewType){
               LayoutType.TODO.ordinal -> {
                   title = view.findViewById(R.id.todo_title)
                   description = view.findViewById(R.id.todo_description_textView)
                   priority = view.findViewById(R.id.todo_priority_indicator)
                   recyclerViewLayout = view.findViewById(R.id.todo_row_background)
               }
               LayoutType.JOKES.ordinal -> { }
           }
       }
    }
}



