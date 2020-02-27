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
    var memberIds: Map<String, Boolean> = HashMap(),
    var chat: Map<Message, Boolean> = HashMap(),
    var journeys: Map<Journey, Boolean> = HashMap()
) {
    fun toGroup(): Group {
        return Group(
            id,
            creatorId,
            memberIds.keys.toList(),
            chat.keys.toList(),
            journeys.keys.toList()
        )
    }
}