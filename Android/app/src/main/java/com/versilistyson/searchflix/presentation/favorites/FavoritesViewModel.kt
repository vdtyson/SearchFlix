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


    private val _favoritesState: MutableLiveData<FavoritesState> by lazy {
        MutableLiveData<FavoritesState>()
    }
    val favoritesState: LiveData<FavoritesState>
        get() = _favoritesState

    private val movieCollector =
        object : FlowCollector<List<Media>> {

            override suspend fun emit(value: List<Media>) {
                val movieList = mutableListOf<Media.Movie>()
                value.forEach {
                    if(it is Media.Movie) movieList.add(it)
                }

                when(val state = _favoritesState.value) {
                    null -> {
                        _favoritesState.postValue(FavoritesState(favoriteMovies = movieList))
                    }

                    else -> {
                        _favoritesState.postValue(state.copy(favoriteMovies = movieList))
                    }
                }
            }
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getFavoriteMoviesFlow(Dispatchers.IO).collect(movieCollector)
        }

    }


}