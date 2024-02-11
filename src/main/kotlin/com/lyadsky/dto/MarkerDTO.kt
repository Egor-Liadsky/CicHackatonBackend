package com.lyadsky.dto

import kotlinx.serialization.Serializable

@Serializable
data class MarkerDTOReceive(
    val description: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val image: String,
    val userCreatedId: String,
    val statusId: Int
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
    val userCreatedId: String,
    val isRepair: Boolean,
    val isValidate: Boolean,
    val status: StatusDTOResponse
)

@Serializable
data class Repair(
    val id: Int,
    val status: Boolean
)