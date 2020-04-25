package com.versilistyson.searchflix.data.datasource.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import com.versilistyson.searchflix.domain.exception.Failure
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieQueryPagedDataSource
constructor(
    private val query: String,
    private val isAdultIncluded: Boolean,
    private val language: String,
    private val fetchFn: (page: Int) -> Call<MoviePagedResponseDto>
) : PageKeyedDataSource<Int, MovieDto>() {

    private val _requestFailureLiveData: MutableLiveData<Failure> by lazy {
        MutableLiveData<Failure>()
    }
    val requestFailureLiveData: LiveData<Failure>
        get() = _requestFailureLiveData

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDto>
    ) {
        val initialPage = 1

        val queryCall =
            fetchFn(1)

        queryCall.async(
            { _, throwable ->
                _requestFailureLiveData.postValue(Failure.ServerError(errorMessage = throwable.message))
            },
            { _, response ->
                val result = response.getResult()

                result.fold(
                    { failure ->
                        _requestFailureLiveData.postValue(failure)
                    },
                    { networkResult ->
                        when (networkResult) {

                            is NetworkResult.Empty -> callback.onResult(emptyList(), null, null)

                            is NetworkResult.Data -> {
                                val mediaDtoList = networkResult.value.movieDtoResults
                                callback.onResult(mediaDtoList, null, 2)
                            }
                        }
                    }
                )
            }
        )

    }

    /* @param data List of items loaded from the PageKeyedDataSource.
    * @param adjacentPageKey Key for subsequent page load (previous page in {@link #loadBefore}
    *                        / next page in {@link #loadAfter}), or {@code null} if there are
    *                        no more pages to load in the current load direction.
    */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDto>) {
        val currentPage = params.key
        val nextPage = params.key + 1

        val queryCall =
            fetchFn(currentPage)

        queryCall.async(
            { _, throwable ->
                _requestFailureLiveData.postValue(Failure.ServerError(errorMessage = throwable.message))

            },
            { _, response ->

                val result = response.getResult()

                result.fold(
                    { failure ->
                        _requestFailureLiveData.postValue(failure)
                    },
                    { networkResult ->
                        when (networkResult) {

                            is NetworkResult.Empty -> callback.onResult(emptyList(), null)

                            is NetworkResult.Data -> {
                                val moviePagedResponseDto = networkResult.value
                                val totalPages = moviePagedResponseDto.totalPages
                                val movieDtoList = moviePagedResponseDto.movieDtoResults
                                when (currentPage) {
                                    totalPages -> callback.onResult(movieDtoList, null)
                                    else -> {
                                        callback.onResult(movieDtoList, nextPage)
                                    }
                                }
                            }
                        }
                    }
                )

            }
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDto>) {
        // Do nothing
    }


    private fun <T> Call<T>.async(
        onFailure: (call: Call<T>, t: Throwable) -> Unit,
        onResponse: (call: Call<T>, response: Response<T>) -> Unit
    ) {
        val callback = object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(call, t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                onResponse(call, response)
            }

        }
        enqueue(callback)
    }

}