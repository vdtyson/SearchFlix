package com.versilistyson.searchflix.data.remote.dto.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.versilistyson.searchflix.data.remote.dto.MovieDto

object MovieAdapter {
    @FromJson
    fun fromJson(reader: JsonReader, delegate: JsonAdapter<MovieDto>): List<MovieDto>? {
        val options = JsonReader.Options.of("results")
        var movieList: MutableList<MovieDto>? = null

        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if(name == "results") {
                reader.beginArray()
                while (reader.hasNext()) {
                    val movieDto = delegate.fromJson(reader)
                    movieDto?.let {
                        when(movieList) {
                            null -> {
                                movieList = mutableListOf()
                                movieList!!.add(it)
                            }
                            else -> {
                                movieList!!.add(it)
                            }
                        }
                    }

                }
                reader.endArray()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()

        return movieList
    }
}