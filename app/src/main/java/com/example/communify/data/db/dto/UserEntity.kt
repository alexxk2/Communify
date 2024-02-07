package com.example.communify.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("user_id") val userId: Int = 0,
    val email: String,
    val gender: String,
    val location: String,
    val name: String,
    val phone: String,
    val picture: String,
    @ColumnInfo("user_name") val username: String,
    @ColumnInfo("unique_key") val uniqueKey: String
)
