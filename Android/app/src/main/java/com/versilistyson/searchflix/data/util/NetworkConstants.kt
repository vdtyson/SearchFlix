package com.versilistyson.searchflix.data.util

object NetworkConstants {
    enum class TMBDImageSize(val path: String) {
        ORIGINAL("original")
    }

    fun getTMBDImageBaseUrlByImageSize(imageSizePath: String) =
        TMDB_IMAGE_BASE_URL + imageSizePath

    private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val TMDB_DEFAULT_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    const val TMDB_V3_BASE_URL = "https://api.themoviedb.org/3/"
}