package com.lyadsky.dto

import kotlinx.serialization.Serializable

@Serializable
data class MarkerDTOReceive(
    val description: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val image: String,
    val userCreatedId: String
)

@Serializable
data class MarkerDTOResponse(
    val id: Int,
    val description: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val image: String,
    val dateCreated: String,
    val userCreatedId: String
)