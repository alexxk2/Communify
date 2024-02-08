package com.example.communify.data.network.dto

data class UserDto(
    val userId: Int = 0,
    val email: String,
    val gender: String,
    val location: LocationDto,
    val name: NameDto,
    val phone: String,
    val picture: PictureDto,
    val username: String,
    val uniqueKey: String? = ""
)