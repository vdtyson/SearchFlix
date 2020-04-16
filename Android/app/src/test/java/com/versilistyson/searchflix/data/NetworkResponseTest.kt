package com.versilistyson.searchflix.data

import com.versilistyson.searchflix.domain.*
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions
import retrofit2.Response

class NetworkResponseTest {
    @Test
    fun `getResult() should return Success with Empty Data`() {
        // GIVEN
        val response = Response.success<String>(null)
        val expected = Either.Right(NetworkSuccess.Empty<String>())
        // WHEN
        val actual = NetworkResponse.getResult(response)
        // THEN
        Assertions.assertTrue {
            when(actual) {

                is Either.Right -> {
                    when(actual.right) {

                        is NetworkSuccess.Data -> false

                        is NetworkSuccess.Empty -> true
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
        val expected = Either.Right(NetworkSuccess.Data(data))
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
        val expected = Either.Right(NetworkSuccess.Data(data))
        // WHEN
        val actual = response.getResult()
        // THEN
        Assertions.assertEquals(expected,actual)
    }

}