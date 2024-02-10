package com.lyadsky.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserReceive(
    val username: String,
    var password: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val password: String
)