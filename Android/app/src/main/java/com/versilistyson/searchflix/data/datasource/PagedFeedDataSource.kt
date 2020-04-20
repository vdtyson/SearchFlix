package com.versilistyson.searchflix.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.presentation.common.NetworkState
import com.versilistyson.searchflix.presentation.common.StateHandler

class PagedFeedDataSource<T>(private val fetcherFn: suspend (page: Int) -> Either<Failure, List<T>>) :
    PageKeyedDataSource<Int, List<T>>(), StateHandler<NetworkState<List<T>>, List<T>> {

    private val _networkState: MutableLiveData<NetworkState<List<T>>> by lazy {
        MutableLiveData<NetworkState<List<T>>>()
    }

    val networkState: LiveData<NetworkState<List<T>>>
    get() = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, List<T>>
    ) {
        updateState(NetworkState.Loading())
        suspend {
            fetcherFn(1).fold(::handleFailure,::handleResult)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, List<T>>) {
        updateState(NetworkState.Loading())
        suspend {
            fetcherFn(params.key).fold(::handleFailure, ::handleResult)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, List<T>>) {}

    override fun updateState(newState: NetworkState<List<T>>) {
        _networkState.postValue(newState)
    }

    override fun handleFailure(failure: Failure) {
        _networkState.postValue(NetworkState.Error(failure))
    }

    override fun handleResult(result: List<T>) {
        _networkState.postValue(NetworkState.Loaded(result))
    }
}