package com.versilistyson.searchflix.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.exception.Failure

class MediaFeedDataSource<T>(val fetcher: suspend (page: Int) -> Either<Failure,T>): PageKeyedDataSource<Int, Media>() {

    sealed class NetworkState<T>() {
        class Loading<T>: NetworkState<T>()
        data class Loaded<T>(val data: T): NetworkState<T>()
        data class Error<T>(val failure: Failure): NetworkState<T>()
    }
    private val _networkState: MutableLiveData<NetworkState<T>> by lazy {
        MutableLiveData<NetworkState<T>>()
    }
    val networkState: LiveData<NetworkState<T>>
    get() = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Media>
    ) {
        _networkState.postValue(NetworkState.Loading())
        suspend {
            fetcher(1).fold(
                {failure -> _networkState.postValue(NetworkState.Error(failure)) },
                {data -> _networkState.postValue(NetworkState.Loaded(data))}
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Media>) {
        _networkState.postValue(NetworkState.Loading())
        suspend {
            fetcher(params.key).fold(
                {failure ->  _networkState.postValue(NetworkState.Error(failure))},
                {data -> _networkState.postValue(NetworkState.Loaded(data))}
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Media>) {
    }


}