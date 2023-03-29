package com.example.carbonenergy.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") var userId: String,
    @SerialName("energy") var energy: Int,
    @SerialName("treesPlanted") var treesPlanted: Int
)