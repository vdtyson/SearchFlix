package com.versilistyson.searchflix.presentation.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>> :
    RecyclerView.Adapter<VH>() {

    open var itemList: MutableList<T> = mutableListOf()

    open override fun getItemCount(): Int = itemList.size

    open fun add(vararg obj: T) {
        obj.forEach {
            itemList.add(it)
            notifyItemInserted(itemList.lastIndex)
        }
    }

    open fun removeItemAt(index: Int) {
        itemList.removeAt(index)
        notifyItemRemoved(index)
    }
}