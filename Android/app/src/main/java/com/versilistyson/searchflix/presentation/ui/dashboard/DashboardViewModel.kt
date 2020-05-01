package com.versilistyson.searchflix.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.versilistyson.searchflix.data.local.SharedPrefManager
import com.versilistyson.searchflix.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DashboardViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val dashboardState by lazy { DashboardState() }

    val popularMovies: LiveData<MediaListStateComponent>
        get() = dashboardState.popularMoviesComponent

    val upcomingMovies: LiveData<MediaListStateComponent>
        get() = dashboardState.upcomingMoviesComponent

    val topRatedMovies: LiveData<MediaListStateComponent>
        get() = dashboardState.topRatedMoviesComponent

    private fun getLanguageAndRegion(): String {

        val languageAndRegion = sharedPrefManager.fetchLanguageAndRegion()

        return languageAndRegion ?: "en-US"
    }

    fun getPopularMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) { dashboardState.popularMoviesComponent.postLoadingState() }

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }
            val result = async(coroutineContext) {
                movieRepository.getPopularMovies(languageAndRegion.await(), page)
            }

            withContext(Dispatchers.Main) {
                result.await().fold(
                    { failure ->
                        dashboardState.popularMoviesComponent.postErrorState(failure)
                    },
                    { moviePagedResponse ->
                        dashboardState.popularMoviesComponent.postDataState(moviePagedResponse.results)
                    }
                )
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) { dashboardState.upcomingMoviesComponent.postLoadingState() }

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }
            val result = async(coroutineContext) {
                movieRepository.getUpcomingMovies((languageAndRegion.await()))
            }

            withContext(Dispatchers.Main) {
                result.await().fold(
                    { failure -> dashboardState.upcomingMoviesComponent.postErrorState(failure) },
                    { movieSingleResponse ->
                        dashboardState.upcomingMoviesComponent.postDataState(movieSingleResponse.movieResults)
                    }
                )
            }
        }
    }

    fun getTopRatedMovies(page: Int = 1) {

        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {dashboardState.topRatedMoviesComponent.postLoadingState()}

            val languageAndRegion = async(coroutineContext) { getLanguageAndRegion() }
            val result = async(coroutineContext) {
                movieRepository.getTopRatedMovies(languageAndRegion.await(), page)
            }

            withContext(Dispatchers.Main) {
                result.await().fold(
                    { failure ->
                        dashboardState.topRatedMoviesComponent.postErrorState(failure)
                    },
                    {moviePagedResponse ->
                        dashboardState.topRatedMoviesComponent.postDataState(moviePagedResponse.results)
                    }
                )
            }
        }
    }
}