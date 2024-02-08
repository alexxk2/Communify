package com.example.communify.domain.profile

import android.net.Uri
import com.example.communify.domain.models.Credentials

interface ProfileInteractor {

    suspend fun updateProfile(userCredentials: Credentials)
    suspend fun deleteAccount(userCredentials: Credentials)
    suspend fun getProfile(): Credentials?
    suspend fun saveImageAndReturnPath(uri: Uri): Uri
    suspend fun deleteOldContacts(uniqueKey: String)
}