package com.versilistyson.searchflix.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.versilistyson.searchflix.domain.entities.Movie
import com.versilistyson.searchflix.domain.usecase.movies.GetPopularMoviesUseCase
import com.versilistyson.searchflix.presentation.common.UIState
import javax.inject.Inject

sealed class DashboardUIState : UIState {
    enum class SearchType {
        MOVIES,
        TV
    }

    data class Querying(val searchType: SearchType, val query: String) : DashboardUIState()
    object Idle : DashboardUIState()
}

class DashboardViewModel
@Inject constructor(val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private val _dashboardUIState: MutableLiveData<DashboardUIState> by lazy {
        MutableLiveData<DashboardUIState>(DashboardUIState.Idle)
    }
    val dashboardUIState: LiveData<DashboardUIState>
        get() = _dashboardUIState
}