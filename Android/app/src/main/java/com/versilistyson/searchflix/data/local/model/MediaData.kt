package com.versilistyson.searchflix.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.domain.entities.MediaType

@Entity(tableName = "media_table")
class MediaData(
    @PrimaryKey
    @ColumnInfo(name = "media_id") val mediaId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Long,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "type") val type: String
) : MappablePersistent<MediaData, Media>() {

    override fun toEntity(): Media {

        when(type) {
            MediaType.MOVIE.name -> {
                return Media.Movie(
                    movieId = mediaId.toInt(),
                    title = title,
                    overview = overview,
                    movieReleaseDate = releaseDate,
                    moviePosterPath = posterPath,
                    movieBackdropPath = backdropPath,
                    movieVoteAverage = voteAverage,
                    movieVoteCount = voteCount.toInt(),
                    isFavorite = isFavorite
                )
            }

            else -> {
                return Media.Movie()
            }
        }
    }

}