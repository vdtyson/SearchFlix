package com.versilistyson.searchflix.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.presentation.common.StateComponent

sealed class MediaListStateComponent : StateComponent {
    object Loading: MediaListStateComponent()
    data class Error(val failure: Failure): MediaListStateComponent()
    data class Data(val value: List<Media>): MediaListStateComponent()
}

fun MutableLiveData<MediaListStateComponent>.postLoadingState() {
    this.postValue(MediaListStateComponent.Loading)
}

fun MutableLiveData<MediaListStateComponent>.postErrorState(failure: Failure) {
    this.postValue(MediaListStateComponent.Error(failure))
}

fun MutableLiveData<MediaListStateComponent>.postDataState(value: List<Media>) {
    this.postValue(MediaListStateComponent.Data(value))
}