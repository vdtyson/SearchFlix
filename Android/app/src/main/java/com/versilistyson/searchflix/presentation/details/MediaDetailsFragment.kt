package com.versilistyson.searchflix.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.databinding.FragmentMediaDetailsBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.presentation.adapters.StreamingServiceAdapter
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MediaDetailsFragment : Fragment(), DataBindingScreen<FragmentMediaDetailsBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    private val viewModel: MediaDetailsViewModel by viewModels {
        daggerViewModelFactory
    }

    override lateinit var binding: FragmentMediaDetailsBinding
    private val args: MediaDetailsFragmentArgs by navArgs()

    private val adapter by lazy {
        StreamingServiceAdapter() {url ->
            navigateToLink(url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityInjector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_media_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAvailableStreamingPlatforms(args.media.id)
        setupRecyclerView()
        renderReleaseDate()
        renderSummary()
        renderRatings()
        renderState()

        binding.tvTitle.text = args.media.name
        Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + args.media.posterPath)
            .into(binding.ivPoster)
        Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + args.media.backdropPath)
            .into(binding.ivBackDrop)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStreamingPlatforms.adapter = adapter
        binding.rvStreamingPlatforms.layoutManager = layoutManager
    }
    private fun renderState() {
        viewModel.mediaDetailsState.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {latestState ->
                when(val streamingPlatformState =latestState.streamingPlaformState) {
                    MediaDetailsState.StreamingPlaformState.Loading -> {}
                    is MediaDetailsState.StreamingPlaformState.Loaded -> {
                        adapter.addAll(streamingPlatformState.availableStreamingLocations)
                    }
                }
            }
        )
    }

    private fun renderSummary() {
        binding.tvSummary.text = args.media.summary
    }
    private fun renderRatings() {
        when (val voteCount = args.media.voteCount) {
            0 -> {
                binding.ratingBar.visibility = View.GONE
                binding.tvNotYetRated.visibility = View.VISIBLE
            }

            else -> {
                val voteAverage = args.media.voteAverage.toFloat()

                Toast.makeText(
                    context,
                    "voteCount $voteCount, voteAverage: $voteAverage",
                    Toast.LENGTH_LONG
                ).show()

                binding.tvNotYetRated.visibility = View.GONE
                binding.ratingBar.visibility = View.VISIBLE

                binding.ratingBar.rating = args.media.voteAverage.toFloat()
            }
        }
    }

    private fun navigateToLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun renderReleaseDate() {
        val datePattern = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        when (val parsedReleaseDate = dateFormat.parse(args.media.releaseDate)) {

            null -> binding.tvReleaseYear.text = ""

            else -> {
                val calendar = GregorianCalendar()
                calendar.time = parsedReleaseDate
                binding.tvReleaseYear.text = calendar.get(Calendar.YEAR).toString()
            }
        }
    }
}
