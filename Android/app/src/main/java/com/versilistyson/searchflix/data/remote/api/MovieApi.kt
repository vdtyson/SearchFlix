package com.versilistyson.searchflix.data.remote.api

import com.versilistyson.searchflix.data.remote.dto.MovieDto
import com.versilistyson.searchflix.data.remote.dto.MoviePagedResponseDto
import com.versilistyson.searchflix.data.remote.dto.MovieSingleResponseDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    fun fetchMovieQueryResults(
        @Query("query") query: String,
        @Query("adult") isAdultIncluded: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<MoviePagedResponseDto>

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<MoviePagedResponseDto>

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<MoviePagedResponseDto>

    @GET("movie/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("language") language: String
    ) : Response<MovieSingleResponseDto>
}

/*
Movie Json

Movie List Json
{
    "page": 0,
    "total_result": 10000,
    "total_pages": 500,
    "results": [
        "popularity": 115.503,
        "vote_count": 153,
        "video": false,
        "poster_path": "/zn7feouGPU8sELez4qvpp0EtgeQ.jpg",
        "id": 624808,
        "adult": false,
        "backdrop_path": "/jjQk4UtMyOnzcqGYiaWiyrLrcEZ.jpg",
        "original_language": "en",
        "original_title": "Love Wedding Repeat",
        "genre_ids": [
            35
        ]
    ]
}

*/