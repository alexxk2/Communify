package com.example.communify.domain.repositories

import android.net.Uri

interface ProfileRepository {

    suspend fun changeName(newName: String)
    suspend fun changeProfileImage(newImage: Uri?)
    suspend fun changeLogin(newLogin: String)
    suspend fun changePassword(newPassword: String)

}