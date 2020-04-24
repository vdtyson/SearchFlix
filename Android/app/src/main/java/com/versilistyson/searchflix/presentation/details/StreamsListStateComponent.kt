package com.versilistyson.searchflix.presentation.details

import com.versilistyson.searchflix.domain.entities.StreamingLocation
import com.versilistyson.searchflix.presentation.common.UIStateComponent

sealed class StreamsListStateComponent: UIStateComponent {
    object Loading : StreamsListStateComponent()
    data class Loaded(val availableStreamingLocations: List<StreamingLocation>) : StreamsListStateComponent()
}