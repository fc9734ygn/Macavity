package com.example.macavity.data.models

data class User(
    val id: String,
    val name: String,
    val lastName: String,
    val location: Pair<Double, Double>,
    val destination: Pair<Double, Double>,
    val avatarUrl: String
    //TODO: is needed?
//    val email: String,
//    val phoneNumber: String
)