package com.versilistyson.searchflix.data.util

import com.versilistyson.searchflix.data.network.dto.Dto
import com.versilistyson.searchflix.domain.entities.Entity
import retrofit2.Response

fun <T> Response<T>.getResult() =
    NetworkResponse.getResult(this)
