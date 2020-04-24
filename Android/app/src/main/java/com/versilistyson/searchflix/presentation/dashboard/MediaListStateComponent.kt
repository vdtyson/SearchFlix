package com.versilistyson.searchflix.presentation.dashboard

import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.presentation.common.UIStateComponent

sealed class MediaListStateComponent : UIStateComponent {
    object Loading : MediaListStateComponent()
    data class Error(val failure: Failure) : MediaListStateComponent()
    data class Data(val value: List<Media>) : MediaListStateComponent()
}