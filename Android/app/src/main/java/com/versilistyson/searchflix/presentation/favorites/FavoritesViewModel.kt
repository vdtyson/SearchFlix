package com.versilistyson.searchflix.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.versilistyson.searchflix.data.repository.MovieRepository
import com.versilistyson.searchflix.domain.entities.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoritesViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {


    private val _liveDataFavoriteMovieList: MutableLiveData<List<Media.Movie>> by lazy {
        MutableLiveData<List<Media.Movie>>()
    }
    val liveDataFavoriteMovieList: LiveData<List<Media.Movie>>
    get() = _liveDataFavoriteMovieList

    private val mediaCollector =
        object : FlowCollector<List<Media>> {

            override suspend fun emit(value: List<Media>) {
                val movieList = mutableListOf<Media.Movie>()
                value.forEach {
                    if(it is Media.Movie) movieList.add(it)
                }
                _liveDataFavoriteMovieList.postValue(movieList)
            }
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getFavoriteMoviesFlow().collect(mediaCollector)
        }

    }


}