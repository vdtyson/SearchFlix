package com.versilistyson.searchflix.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.entities.MediaPagedResponse.MoviePagedResponse
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardState {
    object LoadingCategory : DashboardState()
    data class Failed(val failure: Failure) : DashboardState()
    data class Loaded(
        val popularMovies: List<Media.Movie> = emptyList(),
        val topRatedMovies: List<Media.Movie> = emptyList(),
        val upcomingMovies: List<Media.Movie> = emptyList()
    ) : DashboardState()
}

class DashboardViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _dashboardState: MutableLiveData<DashboardState> by lazy {
        MutableLiveData<DashboardState>()
    }
    val dashboardState: LiveData<DashboardState>
        get() = _dashboardState

    private var lastLoadedState: DashboardState.Loaded? = null

    fun getPopularMovies(language: String = "en-US", page: Int = 1) {

        setLoadingCategoryState()

        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies(language, page)
                .fold(::handleFailure) { moviePagedResponse ->
                    val popularMoviesResult = moviePagedResponse.movieResults
                    setLoadedState(DashboardState.Loaded(popularMovies = popularMoviesResult)) { lastState ->
                        lastState.copy(popularMovies = popularMoviesResult)
                    }
                }
        }
    }

    fun getUpcomingMovies(language: String = "en-US") {

        setLoadingCategoryState()

        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getUpcomingMovies(language)
                .fold(::handleFailure) { movieSingleResponse ->
                    val upcomingMoviesResult = movieSingleResponse.movieResults
                    setLoadedState(DashboardState.Loaded(upcomingMovies = upcomingMoviesResult)) { lastState ->
                        lastState.copy(upcomingMovies = upcomingMoviesResult)
                    }
                }
        }
    }

    fun getTopRatedMovies(language: String = "en-US", page: Int = 1) {

        setLoadingCategoryState()

        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getTopRatedMovies(language, page)
                .fold(::handleFailure) { moviePagedResponse ->
                    val topRatedMoviesResult = moviePagedResponse.movieResults
                    setLoadedState(DashboardState.Loaded(topRatedMovies = topRatedMoviesResult)) { lastState ->
                        lastState.copy(topRatedMovies = topRatedMoviesResult)
                    }
                }
        }

    }

    private fun setLoadedState(
        stateIfLastNull: DashboardState.Loaded,
        applyToLast: (DashboardState.Loaded) -> DashboardState.Loaded
    ) {
        when (lastLoadedState) {
            null -> {
                lastLoadedState = stateIfLastNull
                _dashboardState.postValue(stateIfLastNull)
            }
            else -> {
                val newState = applyToLast(lastLoadedState!!)
                lastLoadedState = newState
                _dashboardState.postValue(newState)
            }
        }
    }

    private fun setLoadingCategoryState() {
        _dashboardState.postValue(DashboardState.LoadingCategory)
    }

    private fun handleFailure(failure: Failure) {
        _dashboardState.postValue(DashboardState.Failed(failure))
    }


    private fun handlePagedResult(moviePagedResponse: MoviePagedResponse) {
        TODO()
    }

    private fun handleResult(result: List<Media.Movie>) {
        _dashboardState.postValue(
            DashboardState.Loaded(result.toMutableList())
        )
    }
}