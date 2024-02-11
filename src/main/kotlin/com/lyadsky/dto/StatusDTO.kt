package com.lyadsky.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatusDTOReceive(
    val name: String
)

@Serializable
data class StatusDTOResponse(
    val id: Int,
    val name: String
)