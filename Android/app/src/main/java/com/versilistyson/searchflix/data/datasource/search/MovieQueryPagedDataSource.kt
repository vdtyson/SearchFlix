package com.versilistyson.searchflix.data.datasource.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.versilistyson.searchflix.data.remote.api.MovieApi
import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import com.versilistyson.searchflix.domain.exception.Failure


class MovieQueryPagedDataSource
@AssistedInject constructor(
    private val movieApi: MovieApi,
    @Assisted private val query: String,
    @Assisted private val isAdultIncluded: Boolean,
    @Assisted private val language: String
) : PageKeyedDataSource<Int, MovieDto>() {

    @AssistedInject.Factory
    interface Factory {
        fun create(
            query: String,
            isAdultIncluded: Boolean,
            language: String
        ): MovieQueryPagedDataSource
    }

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

        val result =
            movieApi.fetchMovieQueryResults(query, isAdultIncluded, language, initialPage)
                .getResult()

        result.fold(
            { failure ->
                _requestFailureLiveData.postValue(failure)
            },
            { networkResult ->

                when (networkResult) {

                    is NetworkResult.Empty -> callback.onResult(emptyList(), null, null)

                    is NetworkResult.Data -> {

                        val moviePagedResponseDto = networkResult.value
                        val movieDtoList = moviePagedResponseDto.movieDtoResults

                        callback.onResult(movieDtoList, null, 2)
                    }
                }
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
        val nextPage = currentPage + 1

        val result =
            movieApi.fetchMovieQueryResults(query, isAdultIncluded, language, currentPage)
                .getResult()

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
                            else -> callback.onResult(movieDtoList, nextPage)
                        }

                    }
                }

            }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDto>) {
        // Do nothing
    }

}