package com.versilistyson.searchflix.presentation.favorites

import com.versilistyson.searchflix.domain.entities.Media

data class FavoritesState(
    val favoriteMovies: List<Media.Movie>
)