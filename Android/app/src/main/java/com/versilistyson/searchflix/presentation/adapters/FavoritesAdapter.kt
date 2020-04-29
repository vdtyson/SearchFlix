package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.domain.entities.Media

class FavoritesAdapter(
    lifecycleOwner: LifecycleOwner,
    private val liveDataMovieList: LiveData<List<Media.Movie>> = MutableLiveData(),
    private val onMediaItemClick: ((media: Media) -> Unit)? = null
): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    init {
        liveDataMovieList.observe(
            lifecycleOwner,
            Observer {
                notifyDataSetChanged()
            }
        )
    }

    inner class FavoritesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val ivSearchPoster: ImageView = view.findViewById(R.id.ivSearchPoster)
        private val ratingsBar: RatingBar = view.findViewById(R.id.ratingsBarSearch)
        private val tvMediaReleaseYear: TextView = view.findViewById(R.id.tvSearchResultMediaReleaseYear)
        private val tvMediaTitle: TextView = view.findViewById(R.id.tvSearchResultMediaTitle)

        fun bindTo(mediaItem: Media.Movie) {

            ratingsBar.rating = (mediaItem.movieVoteAverage.toFloat()) / 2
            tvMediaReleaseYear.text = mediaItem.movieReleaseDate
            tvMediaTitle.text = mediaItem.title

            if (mediaItem.posterPath.isNotBlank()) Picasso.get()
                .load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + mediaItem.posterPath)
                .into(ivSearchPoster)

            onMediaItemClick?.let { fn -> view.setOnClickListener { fn(mediaItem) } }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflatedLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false)
        return FavoritesViewHolder(inflatedLayout)
    }

    override fun getItemCount(): Int = liveDataMovieList.value?.size ?: 0

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val media = liveDataMovieList.value?.get(position)
        media?.let {
            holder.bindTo(media)
        }
    }
}