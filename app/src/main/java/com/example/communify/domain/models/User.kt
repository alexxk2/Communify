package com.example.communify.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int = 0,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val phone: String,
    val picture: Picture,
    val username: String,
    val uniqueKey: String
): Parcelable