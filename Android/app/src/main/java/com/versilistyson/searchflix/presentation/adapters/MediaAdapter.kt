package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.data.util.NetworkConstants
import com.versilistyson.searchflix.domain.entities.Media
import com.versilistyson.searchflix.presentation.adapters.common.BaseViewHolder
import com.versilistyson.searchflix.presentation.adapters.common.LiveDataAdapter

class MediaAdapter(
    lifecycleOwner: LifecycleOwner,
    liveDataMediaList: MutableLiveData<List<Media>> = MutableLiveData(),
    private val onMediaItemClick: ((Media) -> Unit)? = null
) : LiveDataAdapter<Media, MediaAdapter.MediaHolder>(lifecycleOwner, liveDataMediaList) {

    init {
        observeListLiveData()
    }

    inner class MediaHolder(private val view: View) : BaseViewHolder<Media>(view) {

        private val poster: ImageView = view.findViewById(R.id.imageViewMediaPoster)
        private val title: TextView = view.findViewById(R.id.textViewMediaTitle)

        override fun bindTo(obj: Media) {

            Picasso.get().load(NetworkConstants.TMDB_DEFAULT_IMAGE_BASE_URL + obj.posterPath)
                .into(poster)
            title.text = obj.name

            onMediaItemClick?.let { onMediaItemClick ->
                view.setOnClickListener { onMediaItemClick(obj) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_media, parent, false)

        return MediaHolder(inflatedView)
    }


}