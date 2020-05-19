package com.versilistyson.searchflix.data.remote.api

interface ShowApi {
    fun fetchShowQueryResults()
    fun fetchPopularShows()
    fun fetchTopRatedShows()
    fun fetchUpcomingShows()
}