package com.example.communify.domain.profile

import android.net.Uri

interface ProfileInteractor {

    suspend fun changeName(newName: String)
    suspend fun changeProfileImage(newImage: Uri?)
    suspend fun changeLogin(newLogin: String)
    suspend fun changePassword(newPassword: String)
}