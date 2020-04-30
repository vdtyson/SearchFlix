package com.versilistyson.searchflix.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.local.SharedPrefManager
import com.versilistyson.searchflix.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class DashboardViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val dashboardState by lazy { DashboardState() }

    val popularMoviesState: LiveData<MediaListStateComponent>
        get() = dashboardState.popularMoviesComponent

    val upcomingMoviesState: LiveData<MediaListStateComponent>
        get() = dashboardState.upcomingMoviesComponent

    val topRatedMoviesState: LiveData<MediaListStateComponent>
        get() = dashboardState.topRatedMoviesComponent

    private fun getLanguageAndRegion(): String {

        val languageAndRegion = sharedPrefManager.fetchLanguageAndRegion()

        return languageAndRegion ?: "en-US"
    }

    fun getPopularMovies(page: Int = 1) {

        dashboardState.popularMoviesComponent.postLoadingState()
        viewModelScope.launch(Dispatchers.IO) {

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }

            movieRepository.getPopularMovies(languageAndRegion.await(), page).fold(
                { failure -> dashboardState.popularMoviesComponent.postErrorState(failure) },
                { moviePagedResponse ->
                    dashboardState.popularMoviesComponent.postDataState(moviePagedResponse.results)
                }
            )

        }
    }

    fun getUpcomingMovies() {

        dashboardState.upcomingMoviesComponent.postLoadingState()

        viewModelScope.launch(Dispatchers.IO) {

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }
            movieRepository.getUpcomingMovies(languageAndRegion.await()).fold(
                { failure -> dashboardState.upcomingMoviesComponent.postErrorState(failure)},

                { movieSingleResponse ->
                    dashboardState.upcomingMoviesComponent.postDataState(movieSingleResponse.movieResults)
                }
            )
        }
    }

    fun getTopRatedMovies(page: Int = 1) {

        dashboardState.topRatedMoviesComponent.postLoadingState()

        viewModelScope.launch(Dispatchers.IO) {

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }

            movieRepository.getTopRatedMovies(languageAndRegion.await(), page).fold(
                { failure ->
                    dashboardState.topRatedMoviesComponent.postErrorState(failure)
                },
                { moviePagedResponse ->
                    dashboardState.topRatedMoviesComponent.postDataState(moviePagedResponse.movieResults)
                }
            )
        }

    }
}