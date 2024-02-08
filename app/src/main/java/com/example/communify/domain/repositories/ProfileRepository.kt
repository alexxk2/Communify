package com.example.communify.domain.repositories

import android.net.Uri
import com.example.communify.domain.models.Credentials

interface ProfileRepository {

    suspend fun updateProfile(userCredentials: Credentials)
    suspend fun deleteAccount(userCredentials: Credentials)
    suspend fun getProfile(): Credentials?
    suspend fun saveImageAndReturnPath(uri: Uri): Uri

}