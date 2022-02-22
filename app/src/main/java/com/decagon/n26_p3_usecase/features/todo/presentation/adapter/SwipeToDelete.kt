package com.decagon.n26_p3_usecase.features.todo.presentation.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteLeft : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
     recyclerView: RecyclerView,
     viewHolder: RecyclerView.ViewHolder,
     target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    abstract fun onItemSwiped(position: Int)
}
