package com.versilistyson.searchflix.data.network.dto

import com.versilistyson.searchflix.domain.entities.Entity
import com.versilistyson.searchflix.domain.common.Mappable

abstract class Dto<E: Entity> {
    protected abstract val entityMapper: Mappable<E>
    fun mapToEntity() = entityMapper.map()
}