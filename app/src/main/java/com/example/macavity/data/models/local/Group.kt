package com.example.macavity.data.models

data class Group(val id: String, val creatorId: String, val memberIds: List<String>, val chat: List<Message>)