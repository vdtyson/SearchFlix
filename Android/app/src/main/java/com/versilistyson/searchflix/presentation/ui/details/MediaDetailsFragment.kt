package com.versilistyson.searchflix.presentation.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.databinding.FragmentMediaDetailsBinding
import com.versilistyson.searchflix.di.util.DaggerViewModelFactory
import com.versilistyson.searchflix.di.util.activityInjector
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.StreamingServiceAdapter
import com.versilistyson.searchflix.presentation.ui.common.activity.DataBindingScreen
import com.versilistyson.searchflix.presentation.util.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@InternalCoroutinesApi
class MediaDetailsFragment : Fragment(), DataBindingScreen<FragmentMediaDetailsBinding> {

    @Inject
    lateinit var daggerViewModelFactory: DaggerViewModelFactory

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    private val viewModel: MediaDetailsViewModel by viewModels {
        daggerViewModelFactory
    }

    override lateinit var binding: FragmentMediaDetailsBinding
    private val args: MediaDetailsFragmentArgs by navArgs()

    private val adapter by lazy {
        StreamingServiceAdapter() { url ->
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
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_media_details, container, false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bttnExpandOverview.setOnClickListener {
            val toSummaryDialogFragment =
                MediaDetailsFragmentDirections.actionMediaDetailsFragmentToSummaryDialogFragment(
                    args.media.summary
                )
            findNavController().navigate(toSummaryDialogFragment)
        }

        viewModel.getIsFavoriteFlow(args.media.id)
        viewModel.isFavorited.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer(::renderIsFavorited)
        )

        requireActivity().toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_item_favorite -> {


                    val movie = args.media as Media.Movie
                    when (viewModel.isFavorited.value) {
                        true -> {
                            viewModel.persistMovie(movie.copy(isFavorite = false))
                            Toast.makeText(context, "Unfavorited!", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            viewModel.persistMovie(movie.copy(isFavorite = true))
                            Toast.makeText(context, "Favorited!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
            true
        }

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

    override fun onStart() {
        super.onStart()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStreamingPlatforms.adapter = adapter
        binding.rvStreamingPlatforms.layoutManager = layoutManager
    }

    private fun renderIsFavorited(isFavorited: Boolean) {
        when (isFavorited) {
            true -> {
                requireActivity().toolbar
                    .menu.findItem(R.id.menu_item_favorite)
                    .setIcon(R.drawable.ic_bottom_nav_favorite)
            }

            false -> {
                requireActivity().toolbar
                    .menu.findItem(R.id.menu_item_favorite)
                    .setIcon(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

    private fun renderState() {
        viewModel.mediaDetailsState.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { latestState ->
                when (val streamListsState = latestState.streamsListStateComponent) {

                    StreamsListStateComponent.Loading -> {
                        binding.progressBarStreamingServices.visibility = View.VISIBLE
                        binding.tvNotAvailable.visibility = View.GONE
                        binding.rvStreamingPlatforms.visibility = View.GONE
                    }

                    is StreamsListStateComponent.Loaded -> {
                        adapter.addAll(streamListsState.availableStreamingLocations)
                        when {

                            adapter.isEmpty() -> {
                                binding.tvNotAvailable.visibility = View.VISIBLE
                                binding.progressBarStreamingServices.visibility = View.GONE
                                binding.rvStreamingPlatforms.visibility = View.GONE
                            }

                            else -> {
                                binding.rvStreamingPlatforms.visibility = View.VISIBLE
                                binding.progressBarStreamingServices.visibility = View.GONE
                                binding.tvNotAvailable.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        )
    }

    private fun renderSummary() {

        binding.tvSummary.text = args.media.summary

        // TODO: "Find a way to only display read more button when original text is over 4 lines."
    }

    private fun renderRatings() {

        when (val voteCount = args.media.voteCount) {
            0 -> {
                binding.ratingBar.visibility = View.GONE
                binding.tvNotYetRated.visibility = View.VISIBLE
            }

            else -> {
                val rating = (args.media.voteAverage.toFloat()) / 2
                binding.tvNotYetRated.visibility = View.GONE
                binding.ratingBar.visibility = View.VISIBLE

                binding.ratingBar.rating = rating
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
                try {
                    val calendar = GregorianCalendar()
                    calendar.time = parsedReleaseDate
                    binding.tvReleaseYear.text = calendar.get(Calendar.YEAR).toString()
                } catch(e: ParseException) {
                    crashlytics.recordException(e)
                    binding.tvReleaseYear.text = getString(R.string.date_not_found)
                }
            }
        }
    }
}
