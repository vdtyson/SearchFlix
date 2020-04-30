package com.versilistyson.searchflix.presentation.adapters.common

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecylerViewAdapter<T, VH : BaseViewHolder<T>>(initialList: Collection<T> = mutableListOf()) :
    RecyclerView.Adapter<VH>() {

    protected open var itemList: MutableList<T> = initialList.toMutableList()

    override fun getItemCount(): Int = itemList.size

    fun add(vararg items: T) {
        items.forEach { item ->
            itemList.add(item)
            notifyItemInserted(itemList.lastIndex)
        }
    }

    fun update(newList: Collection<T>) {
        itemList = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun clear() {
        if (itemList.isNotEmpty()) {
            val tempLastIndex = itemList.lastIndex
            itemList.clear()
            notifyItemRangeRemoved(0, tempLastIndex)
        }
    }

    fun removeAt(index: Int) {
        itemList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindTo(itemList[position])
    }
}