package com.versilistyson.searchflix.data.datasource.search

import com.versilistyson.searchflix.data.remote.api.StreamLookupApi
import com.versilistyson.searchflix.data.remote.dto.StreamLookupResponseDto
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import javax.inject.Inject

class StreamRemoteSource
@Inject constructor(private val streamLookupApi: StreamLookupApi) {

    suspend fun fetchAvailableStreamingPlatforms(
        mediaId: Int,
        country: String
    ): Either<Failure, NetworkResult<StreamLookupResponseDto>> {

        val response =
            streamLookupApi.fetchAvailableStreamingPlatforms(mediaId, country)

        return response.getResult()
    }
}