package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Group
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.Message
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class GroupFirebase(
    var id: String = "",
    var creatorId: String = "",
    var members: Map<String, Boolean> = HashMap(),
    var chat: Map<String, Message> = HashMap(),
    var journeys: Map<String, JourneyFirebase> = HashMap()
) {
    fun toGroup(): Group {
        return Group(
            id,
            creatorId,
            members.keys.toList(),
            chat.values.toList(),
            journeys.size
        )
    }
}