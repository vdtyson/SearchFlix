package com.versilistyson.searchflix.domain.entities

sealed class Entity

sealed class Media(val id: Int, val name: String = "", val summary: String = "", val imagePath: String = "") : Entity() {
    data class Movie(
        val movieId: Int,
        val title: String = "",
        val releaseDate: String = "",
        val overview: String = "",
        val posterPath: String = "",
        val popularity: Int = 50,
        val voteAverage: Int = 5
    ) : Media(movieId, title,overview, posterPath)

    data class Show(
        val showId: Int = 0,
        val title: String = "",
        val overview: String = "",
        val posterPath: String = ""
    ) : Media(showId,title, overview, posterPath)
}

sealed class Category(val title: String, val mediaList: MutableList<Media>) {
    data class PopularMovies(val movies: MutableList<Media.Movie>) : Category("Popular Movies", movies.toMutableList())
}

