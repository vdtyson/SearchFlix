package com.versilistyson.searchflix.presentation.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentFavoritesBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.FavoritesAdapter
import com.versilistyson.searchflix.presentation.ui.common.activity.DataBindingScreen
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
    private lateinit var adapter: FavoritesAdapter

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
    }


    private fun setupRV() {
        adapter = FavoritesAdapter(viewLifecycleOwner, viewModel.liveDataFavoriteMovieList, ::onMediaItemClicked)

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

    }

    private fun onMediaItemClicked(media: Media) {
        val toMediaDetailsFragment = FavoritesFragmentDirections.actionFavoritesFragmentToMediaDetailsFragment(media, media.name)
        findNavController().navigate(toMediaDetailsFragment)
    }
}
