package com.versilistyson.searchflix.data.remote.api

interface ShowApi {
    val r: Int
    val n: Boolean
    fun fetchShowQueryResults()
    fun fetchPopularShows()
    fun fetchTopRatedShows()
    fun fetchUpcomingShows()
}