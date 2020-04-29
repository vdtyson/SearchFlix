package com.versilistyson.searchflix.presentation.details

import com.versilistyson.searchflix.domain.entities.StreamingLocation
import com.versilistyson.searchflix.presentation.common.StateComponent

sealed class StreamsListStateComponent: StateComponent {
    object Loading : StreamsListStateComponent()
    data class Loaded(val availableStreamingLocations: List<StreamingLocation>) : StreamsListStateComponent()
}