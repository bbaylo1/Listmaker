package com.brandonbaylosis.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListSelectionRecyclerViewAdapter(val lists: ArrayList<TaskList>, val clickListener:
ListSelectionRecyclerViewClickListener) : RecyclerView.Adapter<ListSelectionViewHolder>() {
    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: TaskList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {

        // 1
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_selection_view_holder,
                        parent,
                        false)

        // 2 Creates ListSelectionViewHolder object
        return ListSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.listPosition.text = (position + 1).toString()
        holder.listTitle.text = lists[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun addList(list: TaskList) {
        // 1 Update ArrayList with new TaskList
        lists.add(list)
        // 2 Call notifyItemInserted() to inform Adapter data source is updated
        // Then update RecyclerView
        notifyItemInserted(lists.size-1)
    }
}