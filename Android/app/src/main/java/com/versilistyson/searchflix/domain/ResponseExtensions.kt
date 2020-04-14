package com.versilistyson.searchflix.domain

import retrofit2.Response

fun <T> Response<T>.getResult() =
    NetworkResponse.getResult(this)
