package com.versilistyson.searchflix.data.util

import retrofit2.Response

fun <T> Response<T>.getResult() =
    NetworkResponse.getResult(this)
