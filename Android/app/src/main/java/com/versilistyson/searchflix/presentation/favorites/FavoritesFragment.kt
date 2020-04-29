package com.versilistyson.searchflix.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentFavoritesBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.presentation.adapters.MediaAdapter
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FavoritesFragment : Fragment(), DataBindingScreen<FragmentFavoritesBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val viewModel: FavoritesViewModel by viewModels {
        daggerViewModelFactory
    }

    override lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        viewModel.favoritesState.observe(
            viewLifecycleOwner,
            Observer(::renderState)
        )
    }

    private fun renderState(favoritesState: FavoritesState) {
        adapter.liveDataMediaList.postValue(favoritesState.favoriteMovies)
    }

    private fun setupRV() {
        adapter = MediaAdapter(viewLifecycleOwner)

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}
