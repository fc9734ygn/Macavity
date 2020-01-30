package com.example.macavity.ui.journeyDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.User
import kotlinx.android.synthetic.main.view_passenger.view.*

class PassengersAdapter(private val itemClickListener: (User) -> Unit = {}) :
    ListAdapter<User, RecyclerView.ViewHolder>(ListItemCallback()) {

    class ListItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.passengerStat == newItem.passengerStat
                    && oldItem.driverStat == newItem.driverStat
                    && oldItem.avatarUrl == newItem.avatarUrl
                    && oldItem.email == newItem.email
                    && oldItem.phoneNumber == newItem.phoneNumber
                    && oldItem.isDriver == newItem.isDriver
                    && oldItem.name == newItem.name
                    && oldItem.locationAddress == newItem.locationAddress
                    && oldItem.destinationAddress == newItem.destinationAddress
                    && oldItem.locationCoordinate == newItem.locationCoordinate
                    && oldItem.destinationCoordinate == newItem.destinationCoordinate
                    && oldItem.carNumberPlate == newItem.carNumberPlate
                    && oldItem.carFreeSeats == newItem.carFreeSeats
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as PassengerItemViewHolder).bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_passenger, parent, false)
        return PassengerItemViewHolder(view)
    }

    inner class PassengerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(passenger: User, position: Int) {
            itemView.passenger_name.text = passenger.name
            itemView.passenger_stat.text = String.format(itemView.context.getString(R.string.passenger_stat), passenger.passengerStat)
            setAvatarImage(passenger.avatarUrl)
            itemView.setOnClickListener {
                itemClickListener.invoke(passenger)
            }
        }

        private fun setAvatarImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(itemView.passenger_avatar)
        }
    }
}