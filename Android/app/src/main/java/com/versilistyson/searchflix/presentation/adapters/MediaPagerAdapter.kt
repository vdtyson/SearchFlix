package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.Media

open class MediaPagerAdapter(private var media: List<Media>, private var onViewClickListener: View.OnClickListener? = null) : RecyclerView.Adapter<MediaPagerAdapter.MediaHolder>() {

    inner class MediaHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageViewMediaPoster = view.findViewById<ImageView>(R.id.imageViewMediaPoster)
        private val mediaTitle = view.findViewById<TextView>(R.id.textViewMediaTitle)

        init {
            if(onViewClickListener != null) view.setOnClickListener(onViewClickListener)
        }

        fun bindPhoto(position: Int) {
            Picasso.get().load(media[position].imagePath).into(imageViewMediaPoster)
        }

        fun bindTitle(position: Int) {
            mediaTitle.text = media[position].name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaPagerAdapter.MediaHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_media, parent, false)
        return MediaHolder(inflatedView)
    }

    override fun getItemCount(): Int = media.size

    override fun onBindViewHolder(holder: MediaPagerAdapter.MediaHolder, position: Int) {
        holder.bindPhoto(position)
        holder.bindTitle(position)
    }

    fun setOnViewClickListener(onClickListener: View.OnClickListener) {
        onViewClickListener = onClickListener
    }

}