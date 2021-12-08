package com.decagon.n26_p3_usecase.commons.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokesModelSafe

// create a generic recycler view adapter
class GenericRecyclerViewAdapter<T>(val items: List<T>, private val layout: Int) : RecyclerView.Adapter<GenericRecyclerViewAdapter.GenericViewHolder<T>>() {

    // integrate diff util
    private val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return GenericViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        holder.bind(diffUtil.currentList[position])
    }

    class GenericViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: T)  = with(view) {
            when (item){
                is JokesModelSafe -> { }
                else -> {}
            }


        }
    }
}



