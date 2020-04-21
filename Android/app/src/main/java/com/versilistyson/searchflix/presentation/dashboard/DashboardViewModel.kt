package com.versilistyson.searchflix.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardState {
    data class Failed(val failure: Failure) : DashboardState()
    data class Loaded(val popularMovies: MutableList<Media.Movie>): DashboardState()
}

class DashboardViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _dashboardState: MutableLiveData<DashboardState> by lazy {
        MutableLiveData<DashboardState>()
    }
    val dashboardState: LiveData<DashboardState>
    get() = _dashboardState

    fun getPopularMovies(language: String = "en-US", page: Int = 1) =
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchPopularMovies(language, page).fold(::handleFailure, ::handleResult)
        }

    private fun handleFailure(failure: Failure) {
        _dashboardState.postValue(DashboardState.Failed(failure))
    }

    private fun handleResult(result: List<Media.Movie>) {
        _dashboardState.postValue(
            DashboardState.Loaded(result.toMutableList())
        )
    }
}