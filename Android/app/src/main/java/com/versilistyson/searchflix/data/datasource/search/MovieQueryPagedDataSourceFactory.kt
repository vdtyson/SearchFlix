package com.versilistyson.searchflix.data.datasource.search

import androidx.paging.DataSource
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.versilistyson.searchflix.data.remote.api.MovieApi
import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import retrofit2.Call

class MovieQueryPagedDataSourceFactory
constructor(
    private val query: String,
    private val isAdultIncluded: Boolean,
    private val language: String,
    private val fetchFn: (page: Int) -> Call<MoviePagedResponseDto>
) : DataSource.Factory<Int, MovieDto>() {

    override fun create(): DataSource<Int, MovieDto> =
        MovieQueryPagedDataSource(query, isAdultIncluded, language, fetchFn)

}