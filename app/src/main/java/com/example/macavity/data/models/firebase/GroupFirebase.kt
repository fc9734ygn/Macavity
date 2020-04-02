package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Group
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class GroupFirebase(
    var id: String = "",
    var creatorId: String = "",
    var members: Map<String, Boolean> = HashMap(),
    var journeys: Map<String, JourneyFirebase> = HashMap()
) {
    fun toGroup(): Group {
        return Group(
            id,
            creatorId,
            members.keys.toList(),
            calculateJourneysCompleted(journeys)
        )
    }

    private fun calculateJourneysCompleted(journeys: Map<String, JourneyFirebase>): Int {
        var index = 0
        journeys.forEach {
            if (it.value.timestamp < System.currentTimeMillis()) {
                index++
            }
        }
        return index
    }

}