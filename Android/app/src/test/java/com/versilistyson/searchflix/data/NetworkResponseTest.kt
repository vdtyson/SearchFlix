package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.data.util.NetworkResponse
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.data.util.getResult
import com.versilistyson.searchflix.domain.common.Either
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class NetworkResponseTest {
    @Test
    fun `getResult() should return Success with Empty Data`() {
        // GIVEN
        val response = Response.success<String>(null)
        val expected = Either.Right(NetworkResult.Empty<String>())
        // WHEN
        val actual = NetworkResponse.getResult(response)
        // THEN
        Assertions.assertTrue {
            when(actual) {

                is Either.Right -> {
                    when(actual.right) {

                        is NetworkResult.Data -> false

                        is NetworkResult.Empty -> true
                    }
                }

                else -> false
            }
        }
    }

    @Test
    fun `getResult() should return Success with Data`() {
        // GIVEN
        val data = "Success"
        val response = Response.success<String>(data)
        val expected = Either.Right(NetworkResult.Data(data))
        //WHEN
        val actual = NetworkResponse.getResult(response)
        // THEN
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `getResult() should return ServerError()`() {
        // GIVEN

        // WHEN

        // THEN
        // TODO
    }

    @Test
    fun `Response extension getResult() should return correct value`() {
        // GIVEN
        val data = "Success"
        val response = Response.success(data)
        val expected = Either.Right(NetworkResult.Data(data))
        // WHEN
        val actual = response.getResult()
        // THEN
        Assertions.assertEquals(expected,actual)
    }

}