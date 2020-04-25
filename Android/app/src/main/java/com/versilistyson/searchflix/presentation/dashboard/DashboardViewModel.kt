package com.versilistyson.searchflix.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject




class DashboardViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _dashboardState: MutableLiveData<DashboardState> by lazy {
        MutableLiveData<DashboardState>()
    }

    val dashboardState: LiveData<DashboardState>
        get() = _dashboardState


    private var currentState: DashboardState? = null

    fun getPopularMovies(language: String = "en-US", page: Int = 1) {
        viewModelScope.launch {

            updatePopularMoviesComponent(MediaListStateComponent.Loading)

            launch(Dispatchers.IO) {
                movieRepository.getPopularMovies(language, page).fold(

                    { failure ->
                        val newPopularMoviesComponentState = MediaListStateComponent.Error(failure)
                        updatePopularMoviesComponent(newPopularMoviesComponentState)
                    },

                    { moviePagedResponse ->
                        val newComponentState = MediaListStateComponent.Data(moviePagedResponse.movieResults)
                        updatePopularMoviesComponent(newComponentState)
                    }
                )
            }
        }
    }

    fun getUpcomingMovies(language: String = "en-US") {
        viewModelScope.launch {

            updateUpcomingMoviesComponent(MediaListStateComponent.Loading)

            launch(Dispatchers.IO) {
                movieRepository.getUpcomingMovies(language).fold(
                    { failure ->
                        val newState = MediaListStateComponent.Error(failure)
                        updateUpcomingMoviesComponent(newState)
                    },
                    { movieSingleResponse ->

                        val newState = MediaListStateComponent.Data(movieSingleResponse.movieResults)

                        updateUpcomingMoviesComponent(newState)

                    }
                )
            }

        }
    }

    fun getTopRatedMovies(language: String = "en-US", page: Int = 1) {

        viewModelScope.launch {

            updateTopRatedMoviesComponent(MediaListStateComponent.Loading)

            launch(Dispatchers.IO) {
                movieRepository.getTopRatedMovies(language, page).fold(
                    { failure ->
                        updateTopRatedMoviesComponent(MediaListStateComponent.Error(failure))
                    },
                    { moviePagedResponse ->

                        val topRatedMovieList = moviePagedResponse.movieResults

                        updateTopRatedMoviesComponent(MediaListStateComponent.Data(topRatedMovieList))
                    }
                )
            }
        }
    }

    private fun updateState(
        default: DashboardState,
        whenStateNotNull: (currentState: DashboardState) -> DashboardState
    ) {
        when (currentState) {
            null -> {
                currentState = default
                _dashboardState.postValue(default)
            }
            else -> {
                currentState = whenStateNotNull(currentState!!)
                _dashboardState.postValue(currentState)

            }
        }
    }

    private fun updatePopularMoviesComponent(mediaListStateComponent: MediaListStateComponent) {
        updateState(DashboardState(popularMoviesComponent = mediaListStateComponent)) { currentState ->
            currentState.copy(popularMoviesComponent = mediaListStateComponent)
        }
    }

    private fun updateUpcomingMoviesComponent(mediaListStateComponent: MediaListStateComponent) {
        updateState(DashboardState(upcomingMoviesComponent = mediaListStateComponent)) { currentState ->
            currentState.copy(upcomingMoviesComponent = mediaListStateComponent)
        }
    }

    private fun updateTopRatedMoviesComponent(mediaListStateComponent: MediaListStateComponent) {
        updateState(DashboardState(topRatedMoviesComponent = mediaListStateComponent)) { currentState ->
            currentState.copy(topRatedMoviesComponent = mediaListStateComponent)
        }
    }
}