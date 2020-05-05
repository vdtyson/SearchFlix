package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.util.NetworkResponse
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import com.versilistyson.searchflix.domain.common.Either
import com.versilistyson.searchflix.domain.exception.Failure
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class NetworkUtilTests {

    companion object {
        private val testErrorResponse =
            Response.error<String>(
                403,
                "ERROR!".toResponseBody()
            )

        private val testSuccessfulResponseWithData =
            Response.success("Success!!")

        private val testEmptySuccessfulResponse =
            Response.success<String>(null)
    }

    @Test
    fun `NetworkResponse_getResult() should return NetworkResult from successful response`() {

        // GIVEN
        val expected =
            Either.Right(NetworkResult.Data("Success!!"))

        // WHEN
        val actual = NetworkResponse.getResult(testSuccessfulResponseWithData)

        // THEN
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `NetworkResponse_getResult() should return NetworkResult_Empty from successful response`() {

        // WHEN
        val actual = NetworkResponse.getResult(testEmptySuccessfulResponse)

        // THEN
        Assertions.assertTrue {
            actual.foldAndGet(
                { false },
                { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Empty -> true
                        is NetworkResult.Data -> false
                    }
                }
            )
        }
    }

    @Test
    fun `NetworkResponse_getResult() should return failure from error response`() {
        // GIVEN
        val expected =
            Either.Left(Failure.ServerError(403, testErrorResponse.errorBody().toString()))


        // WHEN
        val actual = NetworkResponse.getResult(testErrorResponse)

        // THEN
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `extension function  Response_getResult() should return failure from error response`() {
        // GIVEN
        val expected =
            Either.Left(Failure.ServerError(403, testErrorResponse.errorBody().toString()))

        // WHEN
        val actual = testErrorResponse.getResult()

        // THEN
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `extension function Response_getResult() should return NetworkResult from successful response`() {
        // GIVEN
        val expected =
            Either.Right(NetworkResult.Data("Success!!"))

        // WHEN
        val actual = testSuccessfulResponseWithData.getResult()

        // THEN
        Assertions.assertEquals(expected, actual)
    }
}