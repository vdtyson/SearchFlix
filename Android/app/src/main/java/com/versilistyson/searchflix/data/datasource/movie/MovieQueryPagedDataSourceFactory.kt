package com.versilistyson.searchflix.data.datasource.movie

import androidx.paging.DataSource
import com.versilistyson.searchflix.data.datasource.movie.MovieQueryPagedDataSource
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
        MovieQueryPagedDataSource(
            query,
            isAdultIncluded,
            language,
            fetchFn
        )

}