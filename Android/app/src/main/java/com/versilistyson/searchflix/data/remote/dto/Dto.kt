package com.versilistyson.searchflix.data.remote.dto

import com.versilistyson.searchflix.domain.entities.Entity
import com.versilistyson.searchflix.domain.common.Mappable

abstract class Dto<E: Entity> {

    protected abstract fun toEntity(): E

    private val entityMapper = object : Mappable<E> {
        override fun map(): E = toEntity()
    }

    fun mapToEntity() = entityMapper.map()
}