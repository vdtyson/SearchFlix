package com.versilistyson.searchflix.presentation.adapters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

abstract class LiveDataAdapter<T, VH : BaseViewHolder<T>>(
    private val lifecycleOwner: LifecycleOwner,
    private val itemListLiveData: MutableLiveData<List<T>>
) : BaseAdapter<T, VH>() {

    init {
        observeListLiveData()
    }

    open val listObserver: Observer<List<T>> =
        Observer { list ->
            list.forEach { item -> add(item) }
        }

    private fun observeListLiveData() {
        itemListLiveData.observe(
            lifecycleOwner,
            listObserver
        )
    }

    fun postValue(items: List<T>) {
        itemListLiveData.postValue(items)
    }
}