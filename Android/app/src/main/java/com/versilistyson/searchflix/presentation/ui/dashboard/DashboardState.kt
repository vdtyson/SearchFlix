package com.versilistyson.searchflix.presentation.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.presentation.ui.common.UIState

class DashboardState: UIState {
    val popularMoviesComponent: MutableLiveData<MediaListStateComponent> by lazy {
        MutableLiveData<MediaListStateComponent>()
    }

    val upcomingMoviesComponent: MutableLiveData<MediaListStateComponent> by lazy {
        MutableLiveData<MediaListStateComponent>()
    }

    val topRatedMoviesComponent: MutableLiveData<MediaListStateComponent> by lazy {
        MutableLiveData<MediaListStateComponent>()
    }
}