package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.Constants
import com.versilistyson.searchflix.domain.entities.Media
import kotlinx.android.synthetic.main.list_item_media.view.*

class MediaAdapter<T : Media>(
    private val mediaList: MutableList<T>,
    private val onMediaItemClickListener: View.OnClickListener?
) : RecyclerView.Adapter<MediaAdapter<T>.MediaHolder>() {

    inner class MediaHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val poster: ImageView = view.findViewById(R.id.imageViewMediaPoster)
        private val title: TextView = view.findViewById(R.id.textViewMediaTitle)

        init {
            onMediaItemClickListener?.let { listener ->
                view.setOnClickListener(listener)
            }
        }

        fun bind(position: Int) {
            val media = mediaList[position]
            Picasso.get().load(Constants.TMDB_IMAGE_BASE_URL + media.imagePath).into(poster)
            title.text = media.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val inflatedView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_media, parent, false)

        return MediaHolder(inflatedView)
    }

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.bind(position)
    }

    fun addAll(items: List<T>) {
        mediaList.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: T) {
        mediaList.add(item)
        notifyItemInserted(mediaList.lastIndex)
    }
}