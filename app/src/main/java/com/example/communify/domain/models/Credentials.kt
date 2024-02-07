package com.example.communify.domain.models

import android.net.Uri

data class Credentials(
    val uniqueKey: String,
    val name: String,
    val login: String,
    val password: String,
    val userImage: Uri?
)
