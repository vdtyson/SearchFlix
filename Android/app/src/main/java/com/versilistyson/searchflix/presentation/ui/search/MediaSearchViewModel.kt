package com.versilistyson.searchflix.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.entities.Media
import javax.inject.Inject

class MediaSearchViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 100
    }
    private val _mediaSearchState: MutableLiveData<MediaSearchState> by lazy {
        MutableLiveData<MediaSearchState>()
    }
    val mediaSearchState: LiveData<MediaSearchState>
        get() = _mediaSearchState


    fun queryMovies(query: String, isAdultIncluded: Boolean = false, language: String = "en-US"): LiveData<PagedList<Media.Movie>> {
        return movieRepository.queryMovies(query, isAdultIncluded, language, PAGE_SIZE)
    }
}