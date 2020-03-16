package com.example.macavity.data.models.local

data class Group(
    val id: String,
    val creatorId: String,
    val members: List<String>,
    val chat: List<Message>,
    val journeysCompleted: Int
)