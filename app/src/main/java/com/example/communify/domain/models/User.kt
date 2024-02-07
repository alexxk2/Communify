package com.example.communify.domain.models

data class User(
    val userId: Int = 0,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val password: String,
    val phone: String,
    val picture: Picture,
    val username: String,
    val uniqueKey: String
)