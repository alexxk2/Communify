package com.example.communify.data.db.dto

import androidx.room.Entity

@Entity("credentials")
data class CredentialsEntity(
    val uniqueKey: String,
    val name: String,
    val login: String,
    val password: String,
    val userImage: String?
)
