package com.versilistyson.searchflix.presentation.dashboard

data class DashboardState (
    val popularMoviesComponent: MediaListStateComponent = MediaListStateComponent.Loading,
    val upcomingMoviesComponent: MediaListStateComponent = MediaListStateComponent.Loading,
    val topRatedMoviesComponent: MediaListStateComponent = MediaListStateComponent.Loading
)