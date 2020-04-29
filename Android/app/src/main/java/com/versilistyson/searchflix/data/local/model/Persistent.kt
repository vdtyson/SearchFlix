package com.versilistyson.searchflix.data.local.model

import com.versilistyson.searchflix.domain.common.Mappable
import com.versilistyson.searchflix.domain.entities.Entity


abstract class Persistent
abstract class MappablePersistent<P: Persistent, E: Entity> : Persistent() {

    protected abstract fun toEntity(): E

    @Transient
    private val mappable = object : Mappable<E> {
        override fun map(): E = toEntity()

    }

    fun mapToEntity() = mappable.map()

}