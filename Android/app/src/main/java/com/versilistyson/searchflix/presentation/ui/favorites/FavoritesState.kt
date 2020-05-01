package com.versilistyson.searchflix.presentation.ui.favorites

import com.versilistyson.searchflix.domain.entities.Media

data class FavoritesState(
    val favoriteMovies: List<Media.Movie>
)