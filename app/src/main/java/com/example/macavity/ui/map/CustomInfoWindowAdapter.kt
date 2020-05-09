package com.example.macavity.ui.map

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.User
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.view_map_marker_window.view.*

class CustomInfoWindowAdapter(private val layoutInflater: LayoutInflater) :
    GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {
        val view: View = layoutInflater.inflate(R.layout.view_map_marker_window, null)
        val user = p0?.tag as User

        view.title.text = p0.title
        view.address.text = p0.snippet
        setAvatarImage(user.avatarUrl, view.profile_avatar)

        return view
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    private fun setAvatarImage(url: String, view: ImageView) {
        Glide.with(view)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(view)
    }
}