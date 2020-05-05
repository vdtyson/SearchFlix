package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.util.NetworkResponse
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.BufferedSource
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class NetworkUtilTests {

    @Test
    fun `NetworkResponse_getResult() should return NetworkResult from successful response`() {

        // GIVEN
        val expected =
            Either.Right(NetworkResult.Data("Success!!"))

        val response = Response.success("Success!!")

        // WHEN
        val actual = NetworkResponse.getResult(response)

        // THEN
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `NetworkResponse_getResult() should return failure from error response`() {
        // GIVEN
        val response =
            Response.error<String>(
                403,
                "ERROR!".toResponseBody()
            )

        val expected =
            Either.Left(Failure.ServerError(403, response.errorBody().toString()))


        // WHEN
        val actual = NetworkResponse.getResult(response)

        // THEN
        Assertions.assertEquals(expected, actual)
    }
}