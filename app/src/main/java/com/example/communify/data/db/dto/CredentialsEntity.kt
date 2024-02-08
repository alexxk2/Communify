package com.example.communify.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("credentials")
data class CredentialsEntity(
    @PrimaryKey (autoGenerate = false) @ColumnInfo("unique_key") val uniqueKey: String,
    val name: String,
    val login: String,
    val password: String,
    @ColumnInfo("user_image") val userImage: String?
)
