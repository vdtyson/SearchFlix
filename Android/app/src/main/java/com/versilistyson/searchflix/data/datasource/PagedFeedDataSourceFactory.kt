package com.versilistyson.searchflix.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure


class PagedFeedDataSourceFactory<T>(private val fetcherFn: suspend (page: Int) -> Either<Failure, List<T>>): DataSource.Factory<Int, List<T>>() {

    private val _sourceLiveData: MutableLiveData<PagedFeedDataSource<T>> by lazy {
        MutableLiveData<PagedFeedDataSource<T>>()
    }
    val sourceLiveData: LiveData<PagedFeedDataSource<T>>
    get() = _sourceLiveData

    override fun create(): DataSource<Int, List<T>> {
        val latestSource = PagedFeedDataSource(fetcherFn)
        _sourceLiveData.postValue(latestSource)
        return latestSource
    }
}