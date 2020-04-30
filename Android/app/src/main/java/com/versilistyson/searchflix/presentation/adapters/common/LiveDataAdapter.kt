package com.versilistyson.searchflix.presentation.adapters.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class LiveDataAdapter<T, VH : BaseViewHolder<T>>(
    val lifecycleOwner: LifecycleOwner,
    val itemListLiveData: MutableLiveData<List<T>>
) : BaseRecylerViewAdapter<T, VH>() {


    open val listObserver: Observer<List<T>> =
        Observer { list ->
            list.forEach { item -> add(item) }
        }

    protected fun observeListLiveData() {
        itemListLiveData.observe(
            lifecycleOwner,
            listObserver
        )
    }

    fun postValue(items: List<T>) {
        itemListLiveData.postValue(items)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = itemListLiveData.value?.get(position)
        item?.let { holder.bindTo(item) }
    }
}