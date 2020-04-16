package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Media


sealed class MediaAdapterState {
    object Normal : MediaAdapterState()
    object Loading: MediaAdapterState()
}

class MediaAdapter(
    private var mediaList: MutableList<Media>
) : RecyclerView.Adapter<MediaAdapter.MediaHolder>() {


    private var mediaAdapterState: MediaAdapterState = MediaAdapterState.Normal

    sealed class MediaHolder(view: View) : RecyclerView.ViewHolder(view) {
        class Loading(val view: View) : MediaHolder(view)
        class Data(val view: View) : MediaHolder(view) {
            private val imageViewMediaPoster =
                view.findViewById<ImageView>(R.id.imageViewMediaPoster)
            private val mediaTitle = view.findViewById<TextView>(R.id.textViewMediaTitle)

            fun bindPhoto(imagePath: String) {
                Picasso.get().load(imagePath).into(imageViewMediaPoster)
            }

            fun bindTitle(title: String) {
                mediaTitle.text = title
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaHolder =
        when (mediaAdapterState) {
            MediaAdapterState.Normal -> {
                val inflatedLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item_media,parent, false)
                MediaHolder.Data(inflatedLayout)
            }
            is MediaAdapterState.Loading -> {
                val inflatedLaout = LayoutInflater.from(parent.context).inflate(R.layout.list_item_loading_media,parent,false)
                MediaHolder.Loading(inflatedLaout)
            }
        }

    override fun getItemCount(): Int = mediaList.size

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        val mediaItem = mediaList[position]
        if (holder is MediaHolder.Data) {
            holder.bindPhoto(mediaItem.imagePath)
            holder.bindTitle(mediaItem.name)
        }
    }

    fun addItems(mediaItems: List<Media>) {
        mediaList.addAll(mediaItems)
        notifyDataSetChanged()
    }

    fun setLoadingState() {
        mediaList.add(Media.Movie())
        mediaAdapterState = MediaAdapterState.Loading
        notifyItemInserted(mediaList.size)
    }

    fun removeLoading() {
        mediaList.removeAt(mediaList.lastIndex)
        mediaAdapterState = MediaAdapterState.Normal
        notifyItemRemoved(mediaList.size)
    }

    fun isLoading(): Boolean =
        when(mediaAdapterState) {
            MediaAdapterState.Normal -> false
            MediaAdapterState.Loading -> true
        }


}