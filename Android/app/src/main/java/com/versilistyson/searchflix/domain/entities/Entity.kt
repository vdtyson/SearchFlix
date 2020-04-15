package com.versilistyson.searchflix.domain.entities

sealed class Entity

data class Movie(
    val title: String = "",
    val releaseDate: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val popularity: Int = 50,
    val voteAverage: Int = 5
) : Entity()
