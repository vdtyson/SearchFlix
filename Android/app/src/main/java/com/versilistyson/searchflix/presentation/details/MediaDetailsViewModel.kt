package com.versilistyson.searchflix.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.StreamRepository
import com.versilistyson.searchflix.domain.entities.StreamLocationsResult
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse
import com.versilistyson.searchflix.domain.entities.StreamingLocation
import com.versilistyson.searchflix.domain.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MediaDetailsState(val streamingPlaformState: StreamingPlaformState) {
    sealed class StreamingPlaformState {
        object Loading : StreamingPlaformState()
        data class Loaded(val availableStreamingLocations: List<StreamingLocation>) : StreamingPlaformState()
    }
}
class MediaDetailsViewModel
@Inject constructor(private val streamRepository: StreamRepository): ViewModel() {

    private val _mediaDetailsState: MutableLiveData<MediaDetailsState> by lazy {
        MutableLiveData<MediaDetailsState>()
    }
    val mediaDetailsState
    get() = _mediaDetailsState

    fun getAvailableStreamingPlatforms(
        mediaId: Int,
        country: String = "us"
    ) {
        _mediaDetailsState.postValue(MediaDetailsState(streamingPlaformState = MediaDetailsState.StreamingPlaformState.Loading))

        viewModelScope.launch(Dispatchers.IO) {
                streamRepository.getAvailableStreamingPlatforms(mediaId, country).fold(::handleFailure, ::handleResult)
        }

    }

    private fun handleResult(streamLookupResponse: StreamLookupResponse) {
        val streamLocations = streamLookupResponse.streamLocationsResult.streamLocations

        _mediaDetailsState.postValue(
            MediaDetailsState(streamingPlaformState = MediaDetailsState.StreamingPlaformState.Loaded(streamLocations)))
    }
    private fun handleFailure(failure: Failure) {

    }
}