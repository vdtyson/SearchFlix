package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.domain.entities.Media

class MediaPagedAdapter(private val onMediaItemClick: ((media: Media) -> Unit)? = null) :
    PagedListAdapter<Media.Movie, MediaPagedAdapter.MediaViewHolder>(DIFF_CALLBACK) {

    inner class MediaViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val ivSearchPoster: ImageView = view.findViewById(R.id.ivSearchPoster)

        fun bindTo(mediaItem: Media.Movie) {
            if (mediaItem.posterPath.isNotBlank()) Picasso.get()
                .load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + mediaItem.posterPath)
                .into(ivSearchPoster)

            onMediaItemClick?.let { fn -> view.setOnClickListener { fn(mediaItem) } }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaPagedAdapter.MediaViewHolder {
        val inflatedLayount =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false)
        return MediaViewHolder(inflatedLayount)
    }

    override fun onBindViewHolder(holder: MediaPagedAdapter.MediaViewHolder, position: Int) {
        val mediaItem = getItem(position)
        mediaItem?.let {
            holder.bindTo(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Media.Movie>() {
            override fun areItemsTheSame(oldItem: Media.Movie, newItem: Media.Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Media.Movie, newItem: Media.Movie): Boolean {
                return oldItem == newItem
            }


        }
    }

}