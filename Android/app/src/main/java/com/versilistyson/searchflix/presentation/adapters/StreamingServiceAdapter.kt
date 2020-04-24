package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.StreamingLocation
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_item_streaming_platforms.view.*

class StreamingServiceAdapter(
    private val streamLocationList: MutableList<StreamingLocation> = mutableListOf()
) : RecyclerView.Adapter<StreamingServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivStreamingServiceIcon)
        val tvServiceTitle: TextView = view.findViewById(R.id.tvStreamingServiceTitle)
        fun bindTo(streamingLocation: StreamingLocation) {
            Picasso.get().load(streamingLocation.iconPath).into(ivIcon)
            tvServiceTitle.text = streamingLocation.displayName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val inflatedLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_streaming_platforms, parent, false)
        return ServiceViewHolder(inflatedLayout)
    }

    override fun getItemCount(): Int = streamLocationList.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val streamingLocation = streamLocationList[position]
        holder.bindTo(streamingLocation)
    }

    fun addAll(newStreamingLocations: List<StreamingLocation>) {
        streamLocationList.addAll(newStreamingLocations)
        notifyDataSetChanged()
    }
}