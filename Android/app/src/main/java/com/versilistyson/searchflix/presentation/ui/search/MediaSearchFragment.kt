package com.versilistyson.searchflix.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentMediaSearchBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.MediaPagedAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@InternalCoroutinesApi
class MediaSearchFragment : Fragment() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory
    private val viewModel: MediaSearchViewModel by viewModels {
        daggerViewModelFactory
    }

    lateinit var binding: FragmentMediaSearchBinding

    private val args: MediaSearchFragmentArgs by navArgs()

    lateinit var adapter: MediaPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()
        render()
    }

    private fun render() {
        viewModel.queryMovies(args.query).observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(null)
                adapter.notifyDataSetChanged()
                adapter.submitList(it)
            }
        )
    }

    private fun setupRV() {
        initAdapter()
        binding.cardStackView.adapter = adapter
        binding.cardStackView.layoutManager = provideLayoutManager()
    }

    private fun onMediaItemClick(media: Media) {
        val toMediaDetailsFragment = MediaSearchFragmentDirections.actionMediaSearchFragmentToMediaDetailsFragment(media, media.name)
        findNavController().navigate(toMediaDetailsFragment)
    }

    private fun initAdapter() {
        adapter = MediaPagedAdapter(::onMediaItemClick)
    }

    private fun provideLayoutManager() =
        GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
}
