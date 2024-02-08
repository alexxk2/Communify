package com.example.communify.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: Int
): Parcelable