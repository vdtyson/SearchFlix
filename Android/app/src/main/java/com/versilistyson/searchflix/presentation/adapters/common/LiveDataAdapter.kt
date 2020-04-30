package com.versilistyson.searchflix.presentation.adapters.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class LiveDataAdapter<T, VH : BaseViewHolder<T>>(
    protected val lifecycleOwner: LifecycleOwner,
    protected val itemListLiveData: MutableLiveData<List<T>>
) : BaseRecylerViewAdapter<T, VH>() {

    open val listObserver: Observer<List<T>> =
        Observer { list -> list.forEach { item -> update(list) } }

    protected fun observeListLiveData() {
        itemListLiveData.observe(
            lifecycleOwner,
            listObserver
        )
    }


    fun postValue(items: List<T>) { itemListLiveData.postValue(items) }
}