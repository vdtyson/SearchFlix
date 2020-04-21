package com.versilistyson.searchflix.data.remote.api

import com.versilistyson.searchflix.data.remote.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    suspend fun fetchMoviesFromQuery(
        @Query("query") query: String,
        @Query("adult") isAdultIncluded: Boolean,
        @Query("page") page: Int,
        @Query("language") language: String
    ) : Response<List<MovieDto>>

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<List<MovieDto>>
}