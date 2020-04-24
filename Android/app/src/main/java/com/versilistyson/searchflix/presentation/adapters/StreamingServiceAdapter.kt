package com.versilistyson.searchflix.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.versilistyson.searchflix.R
import com.versilistyson.searchflix.domain.entities.StreamingLocation

class StreamingServiceAdapter(
    private val streamLocationList: MutableList<StreamingLocation> = mutableListOf(),
    private val onBadgeClick: ((path: String) -> Unit)? = null
) : RecyclerView.Adapter<StreamingServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ivIcon: ImageView = view.findViewById(R.id.ivStreamingServiceIcon)
        private val tvServiceTitle: TextView = view.findViewById(R.id.tvStreamingServiceTitle)
        private val cvStreamingServiceBadge: CardView = view.findViewById(R.id.cvStreamingServiceBadge)

        fun bindTo(streamingLocation: StreamingLocation) {

            tvServiceTitle.text = streamingLocation.displayName

            Picasso.get().load(streamingLocation.iconPath).into(ivIcon)

            onBadgeClick?.let {fn ->
                cvStreamingServiceBadge.setOnClickListener { fn(streamingLocation.pathToMedia) }
            }

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

    fun isEmpty() = streamLocationList.isEmpty()

}