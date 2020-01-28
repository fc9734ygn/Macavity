package com.example.macavity.ui.calendar

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.Journey
import kotlinx.android.synthetic.main.item_calendar_journey.view.*

class JourneysAdapter(private val itemClickListener: (Journey) -> Unit = {}) :
    ListAdapter<Journey, RecyclerView.ViewHolder>(ListItemCallback()) {

    class ListItemCallback : DiffUtil.ItemCallback<Journey>() {
        override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
            return oldItem.freeSeats == newItem.freeSeats
                    && oldItem.takenSeats == newItem.takenSeats
                    && oldItem.passengers == newItem.passengers
                    && oldItem.timeStamp == newItem.timeStamp
                    && oldItem.driverId == newItem.driverId
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as JourneyItemViewHolder).bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_journey, parent, false)
        return JourneyItemViewHolder(view)
    }

    inner class JourneyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Journey, position: Int) {
            itemView.driver_name.text = model.driverName
            itemView.depature.text = "123" //todo: format time
            itemView.seats_left.text = String.format(itemView.context.getString(R.string.item_journey_seats_left), model.takenSeats, model.freeSeats)
            if (model.takenSeats >= model.freeSeats){
                itemView.button.isEnabled = false
            }

            itemView.button.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }
}
