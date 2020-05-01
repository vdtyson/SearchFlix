package com.versilistyson.searchflix.presentation.adapters.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(private val view: View): RecyclerView.ViewHolder(view) {
    abstract fun bindTo(obj: T)
}