package com.example.macavity.ui.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import kotlinx.android.synthetic.main.view_member.view.*

class MembersAdapter(private val itemClickListener: (User) -> Unit = {}) :
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
                    && oldItem.driver == newItem.driver
                    && oldItem.name == newItem.name
                    && oldItem.home == newItem.home
                    && oldItem.destination == newItem.destination
                    && oldItem.carNumberPlate == newItem.carNumberPlate
                    && oldItem.carFreeSeats == newItem.carFreeSeats
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_member, parent, false)
        return MemberItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MemberItemViewHolder).bind(item, position)
    }

    inner class MemberItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(member: User, position: Int) {
            itemView.member_name.text = member.name
            itemView.member_driver_stat.text = String.format(
                itemView.context.getString(R.string.journey_details_driver_stat),
                member.driverStat
            )
            itemView.member_passenger_stat.text = String.format(
                itemView.context.getString(R.string.journey_details_passenger_stat),
                member.passengerStat
            )
            setAvatarImage(member.avatarUrl)
            itemView.setOnClickListener {
                itemClickListener.invoke(member)
            }
        }

        private fun setAvatarImage(url: String) {
            Glide.with(itemView)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(itemView.member_avatar)
        }
    }

    override fun submitList(list: List<User>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}