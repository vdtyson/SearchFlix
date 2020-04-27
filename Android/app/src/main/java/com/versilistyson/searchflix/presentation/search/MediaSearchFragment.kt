package com.versilistyson.searchflix.presentation.search

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
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.databinding.FragmentMediaSearchBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.MediaPagedAdapter
import com.versilistyson.searchflix.presentation.util.CardStackLayoutManagerFactory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MediaSearchFragment : Fragment() {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory
    private val viewModel: MediaSearchViewModel by viewModels {
        daggerViewModelFactory
    }

    lateinit var binding: FragmentMediaSearchBinding

    val args: MediaSearchFragmentArgs by navArgs()

    lateinit var adapter: MediaPagedAdapter
    lateinit var cardStackLayoutManager: CardStackLayoutManager

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

        cardStackLayoutManager = provideCardStackLayoutManager()
        adapter = provideAdapter()
        adapter.submitList(null)

        setupRV()

        viewModel.queryMovies(args.query).observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )
    }

    private fun setupRV() {
        binding.cardStackView.adapter = adapter
        binding.cardStackView.layoutManager = cardStackLayoutManager
    }

    private fun onMediaItemClick(media: Media) {
        val toMediaDetailsFragment = MediaSearchFragmentDirections.actionMediaSearchFragmentToMediaDetailsFragment(media)
        findNavController().navigate(toMediaDetailsFragment)
    }

    private fun provideAdapter() =
        MediaPagedAdapter(::onMediaItemClick)

    private fun provideCardStackLayoutManager() =
        CardStackLayoutManagerFactory.create(
            context = context,
            stackFrom = StackFrom.TopAndRight,
            visibleCount = 3,
            canScrollVertical = false,
            canScrollHorizontal = true,
            swipeableMethod = SwipeableMethod.Manual
        )


}
