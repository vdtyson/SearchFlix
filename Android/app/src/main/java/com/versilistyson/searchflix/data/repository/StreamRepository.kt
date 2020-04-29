package com.versilistyson.searchflix.data.repository

import com.versilistyson.searchflix.data.datasource.stream.StreamRemoteSource
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse
import com.versilistyson.searchflix.domain.exception.Failure
import javax.inject.Inject

class StreamRepository
@Inject constructor(private val streamRemoteSource: StreamRemoteSource) {

    suspend fun getAvailableStreamingPlatforms(
        mediaId: Int,
        country: String
    ) : Either<Failure, StreamLookupResponse> {
        val networkResult =
            streamRemoteSource.fetchAvailableStreamingPlatforms(mediaId, country)

        return networkResult.foldAndMap(
            { failure ->
                failure
            },
            { result ->
                when(result) {
                    is NetworkResult.Empty -> StreamLookupResponse()
                    is NetworkResult.Data -> result.value.mapToEntity()
                }
            }
        )
    }
}