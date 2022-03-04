package com.decagon.n26_p3_usecase.commons.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.log
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils
import com.decagon.n26_p3_usecase.features.todo.model.Priority
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.decagon.n26_p3_usecase.features.todo.presentation.viewController.ListFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

// create a generic recycler view adapter
class GenericRecyclerViewAdapter<T>(private val items: List<T>, private val layout: Int) : RecyclerView.Adapter<GenericRecyclerViewAdapter.GenericViewHolder<T>>() {

    private val diffCallback = object : DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<T>) = differ.submitList(list)


    enum class  LayoutType{
        TODO, RUNS, JOKES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        submitList(items)
       return when(layout){
            LayoutType.TODO.ordinal -> {
                GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false), LayoutType.TODO.ordinal)
            }
           LayoutType.JOKES.ordinal -> {
             GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.todo_recycler_view_item, parent, false), LayoutType.JOKES.ordinal)
           }
           LayoutType.RUNS.ordinal -> {
               GenericViewHolder(
                   LayoutInflater.from(parent.context)
                       .inflate(R.layout.item_run, parent, false),
                   LayoutType.RUNS.ordinal
               )
           }
            else -> {
                GenericViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false), LayoutType.RUNS.ordinal)
            }
        }
    }

    override fun getItemCount(): Int { return differ.currentList.size }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {

        when(holder.viewType){
            LayoutType.TODO.ordinal -> {
                holder.title?.text = (differ.currentList[position] as TodoData).title
                holder.description?.text = (differ.currentList[position] as TodoData).description
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(differ.currentList[position] as TodoData)
                holder.recyclerViewLayout!!.setOnClickListener {
                    holder.itemView.findNavController().navigate(action)
                }

                when ((differ.currentList[position] as TodoData).priority) {
                    Priority.HIGH -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.red_900))
                    Priority.MEDIUM -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.yellow_700))
                    Priority.LOW -> holder.priority?.setCardBackgroundColor(ContextCompat.getColor(
                        holder.priority!!.context, R.color.green_500))
                }
            }
            LayoutType.RUNS.ordinal -> {
                val run = differ.currentList[position] as Run

                holder.itemView.apply {
                    holder.image?.let { Glide.with(this).load(run.image).into(it) }
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = run.timestamp // date in milliseconds
                    }
                    val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                    holder.date?.text = dateFormat.format(calendar.time)

                    val avgSpeed = "${run.avgSpeed}km/h"
                    holder.averageSpeed?.text = avgSpeed

                    val distanceInKm = "${run.distanceInMeters / 1000f}km"
                    holder.distance?.text = distanceInKm

                    holder.time?.text = TrackingUtils.getFormattedStopWatchTime(run.timeInMillis)

                    val caloriesBurned = "${run.caloriesBurned}kcal"
                    holder.calories?.text = caloriesBurned
                }
            }
        }

    }

    class GenericViewHolder<T>(val view: View, val viewType:Int) : RecyclerView.ViewHolder(view) {

       // todos recycler view item
        var title: TextView? = null
        var description: TextView? = null
        var priority: CardView? = null
        var recyclerViewLayout: View? = null

        // Run recycler view items
        var image: ImageView? = null
        var date: TextView? = null
        var time: TextView? = null
        var distance: TextView? = null
        var averageSpeed: TextView? = null
        var calories: TextView? = null

        init {
            log("view type is $viewType")

           when(viewType){
               LayoutType.TODO.ordinal -> {
                   title = view.findViewById(R.id.todo_title)
                   description = view.findViewById(R.id.todo_description_textView)
                   priority = view.findViewById(R.id.todo_priority_indicator)
                   recyclerViewLayout = view.findViewById(R.id.todo_row_background)
               }
               LayoutType.JOKES.ordinal -> { }
               LayoutType.RUNS.ordinal -> {
                   date = view.findViewById(R.id.tvDate)
                   time = view.findViewById(R.id.tvTime)
                   image = view.findViewById(R.id.ivRunImage)
                   distance = view.findViewById(R.id.tvDistance)
                   averageSpeed = view.findViewById(R.id.tvAverageSpeed)
                   calories = view.findViewById(R.id.tvCalories)
                   recyclerViewLayout = view.findViewById(R.id.run_item_layout)
               }
           }
       }
    }
}



