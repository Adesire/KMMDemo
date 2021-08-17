package com.adesire.kmmdemo.shared

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val uid: String,
    val name: String,
    val description: String,
    val type: String,
    val logo: String,
    val review: String,
)