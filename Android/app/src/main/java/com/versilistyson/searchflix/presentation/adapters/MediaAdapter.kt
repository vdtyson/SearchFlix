package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.domain.entities.Media

class MediaAdapter(
    lifecycleOwner: LifecycleOwner,
    private val liveDataMediaList: MutableLiveData<List<Media>>,
    private val onMediaItemClick: ((Media) -> Unit)?
) : RecyclerView.Adapter<MediaAdapter.MediaHolder>() {

    init {
        liveDataMediaList.observe(
            lifecycleOwner,
            Observer {
                notifyDataSetChanged()
            }
        )
    }

    inner class MediaHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val poster: ImageView = view.findViewById(R.id.imageViewMediaPoster)
        private val title: TextView = view.findViewById(R.id.textViewMediaTitle)

        fun bindTo(media: Media?) {
            media?.let {
                Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + media.posterPath).into(poster)
                title.text = media.name

                onMediaItemClick?.let { onMediaItemClick ->
                    view.setOnClickListener { onMediaItemClick(media) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {

        val inflatedView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_media, parent, false)

        return MediaHolder(inflatedView)
    }

    override fun getItemCount(): Int = liveDataMediaList.value?.size ?: 0

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {

        val mediaList = liveDataMediaList.value
        holder.bindTo(mediaList?.get(position))
    }
}