package com.example.communify.data.media_storage

import android.net.Uri

interface ImageSaver {

    suspend fun saveImageAndReturnPath(uri: Uri): Uri
}