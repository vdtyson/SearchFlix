package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Media


sealed class MediaAdapterState {
    object Normal : MediaAdapterState()
    object Loading: MediaAdapterState()
}

class MediaPagedAdapter(private val onMediaClickListener: View.OnClickListener): PagedListAdapter<Media,MediaPagedAdapter.MediaViewHolder>(DIFF_CALLBACK) {

    inner class MediaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var mediaTitle = view.findViewById<TextView>(R.id.textViewMediaTitle)
        private var mediaPoster = view.findViewById<ImageView>(R.id.imageViewMediaPoster)
        init {
            view.setOnClickListener(onMediaClickListener)
        }
        fun bindTo(mediaItem: Media?) {
            mediaItem?.let {
                Picasso.get().load(mediaItem.imagePath).into(mediaPoster)
                mediaTitle.text = mediaItem.name
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaPagedAdapter.MediaViewHolder {
        val inflatedLayount = LayoutInflater.from(parent.context).inflate(R.layout.list_item_media,parent,false)
        return MediaViewHolder(inflatedLayount)
    }

    override fun onBindViewHolder(holder: MediaPagedAdapter.MediaViewHolder, position: Int) {
        val mediaItem = getItem(position)
        holder.bindTo(mediaItem)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Media>() {
            override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem == newItem
            }


        }
    }

}