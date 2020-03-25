package com.example.macavity.ui.calendar

import android.text.format.DateUtils.getRelativeTimeSpanString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.UpcomingJourney
import com.example.macavity.data.models.local.User
import kotlinx.android.synthetic.main.view_journey.view.*

class JourneysAdapter(private val itemClickListener: (UpcomingJourney) -> Unit = {}) :
    ListAdapter<UpcomingJourney, RecyclerView.ViewHolder>(ListItemCallback()) {

    class ListItemCallback : DiffUtil.ItemCallback<UpcomingJourney>() {
        override fun areItemsTheSame(oldItem: UpcomingJourney, newItem: UpcomingJourney): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UpcomingJourney, newItem: UpcomingJourney): Boolean {
            return oldItem.freeSeats == newItem.freeSeats
                    && oldItem.passengerIds == newItem.passengerIds
                    && oldItem.timestamp == newItem.timestamp
                    && oldItem.driverAvatarUrl == newItem.driverAvatarUrl
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as JourneyItemViewHolder).bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_journey, parent, false)
        return JourneyItemViewHolder(view)
    }

    inner class JourneyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: UpcomingJourney, position: Int) {
            val relativeTimeString = getRelativeTimeSpanString(model.timestamp,System.currentTimeMillis(),0).toString().toLowerCase()
            itemView.departure.text = String.format(itemView.context.getString(R.string.journey_leaving_in),relativeTimeString)
            val seatsTaken = if (model.passengerIds.isNullOrEmpty()) 0 else model.passengerIds.size
            itemView.seats_left.text = String.format(itemView.context.getString(R.string.item_journey_seats_left), model.freeSeats - seatsTaken)
            setAvatarImage(model.driverAvatarUrl)

            itemView.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }

        private fun setAvatarImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(itemView.driver_avatar)
        }
    }

    override fun submitList(list: List<UpcomingJourney>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}
