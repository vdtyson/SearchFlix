package com.versilistyson.searchflix

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.databinding.FragmentMediaDetailsBinding
import com.versilistyson.searchflix.presentation.common.activity.DataBindingScreen
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MediaDetailsFragment : Fragment(), DataBindingScreen<FragmentMediaDetailsBinding> {

    override lateinit var binding: FragmentMediaDetailsBinding
    private val args: MediaDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datePattern = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(datePattern, Locale.getDefault())

        when(val parsedReleaseDate = dateFormat.parse(args.media.releaseDate)) {

            null -> binding.tvReleaseYear.text = ""

            else -> {
                val calendar = GregorianCalendar()
                calendar.time = parsedReleaseDate
                binding.tvReleaseYear.text = calendar.get(Calendar.YEAR).toString()
            }
        }

        binding.tvTitle.text = args.media.name
        Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + args.media.posterPath).into(binding.ivPoster)
        Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + args.media.backdropPath).into(binding.ivBackDrop)
    }
}
