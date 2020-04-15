package com.versilistyson.searchflix.domain.common

interface Mappable<T> {
    fun map(): T
}