package com.versilistyson.searchflix.data.datasource

import androidx.paging.DataSource
import com.versilistyson.searchflix.domain.entities.Media


class FeedDataFactory: DataSource.Factory<Int, Media.Movie>() {
    override fun create(): DataSource<Int, Media.Movie> {
        TODO("Create data ffactory for pagination")
    }
}