package com.versilistyson.searchflix.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.data.repository.StreamRepository
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse
import com.versilistyson.searchflix.domain.entities.StreamingLocation
import com.versilistyson.searchflix.domain.exception.Failure
import com.versilistyson.searchflix.presentation.common.UIState
import com.versilistyson.searchflix.presentation.common.UIStateComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
class MediaDetailsViewModel
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val streamRepository: StreamRepository
): ViewModel() {

    private val _mediaDetailsState: MutableLiveData<MediaDetailsState> by lazy {
        MutableLiveData<MediaDetailsState>()
    }
    val mediaDetailsState
    get() = _mediaDetailsState

    private val _isFavorited: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isFavorited: LiveData<Boolean>
    get() = _isFavorited

    private val isFavoriteFlowCollector = object : FlowCollector<Boolean?> {
        override suspend fun emit(value: Boolean?) {
            value?.let {
                _isFavorited.postValue(value)
            }
        }

    }

    fun getIsFavoriteFlow(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getFlowOfIsFavorite(id).collect(isFavoriteFlowCollector)
        }
    }


    fun persistMovie(movie: Media.Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.save(movie)
        }
    }

    fun getAvailableStreamingPlatforms(
        mediaId: Int,
        country: String = "us"
    ) {
        _mediaDetailsState.postValue(MediaDetailsState(streamsListStateComponent = StreamsListStateComponent.Loading))

        viewModelScope.launch(Dispatchers.IO) {
                streamRepository.getAvailableStreamingPlatforms(mediaId, country).fold(::handleFailure, ::handleResult)
        }

    }

    private fun handleResult(streamLookupResponse: StreamLookupResponse) {
        val streamLocations = streamLookupResponse.streamLocationsResult.streamLocations

        _mediaDetailsState.postValue(
            MediaDetailsState(streamsListStateComponent = StreamsListStateComponent.Loaded(streamLocations)))
    }
    private fun handleFailure(failure: Failure) {

    }
}